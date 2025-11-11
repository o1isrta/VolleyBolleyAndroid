package cy.volleybolley.core.data.network.plugin

import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.auth.domain.api.usecase.RefreshAccessTokenUseCase
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpReceivePipeline
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.takeFrom
import io.ktor.util.AttributeKey
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class TokenRefreshPlugin(
    private val loginDataRepository: LoginDataRepository,
    private val refreshAccessTokenUseCase: RefreshAccessTokenUseCase,
    private val onUnauthorized: suspend () -> Unit
) {

    class Config {
        var loginDataRepository: LoginDataRepository? = null
        var refreshAccessTokenUseCase: RefreshAccessTokenUseCase? = null
        var onUnauthorized: (suspend () -> Unit)? = null
    }

    companion object Plugin : HttpClientPlugin<Config, TokenRefreshPlugin> {
        override val key = AttributeKey<TokenRefreshPlugin>("TokenRefreshPlugin")

        private var isRefreshing = false
        private val refreshMutex = Mutex()

        override fun prepare(block: Config.() -> Unit): TokenRefreshPlugin {
            val config = Config().apply(block)
            return TokenRefreshPlugin(
                loginDataRepository = requireNotNull(config.loginDataRepository) {
                    "loginDataRepository must be provided"
                },
                refreshAccessTokenUseCase = requireNotNull(config.refreshAccessTokenUseCase) {
                    "refreshAccessTokenUseCase must be provided"
                },
                onUnauthorized = config.onUnauthorized ?: {}
            )
        }

        override fun install(plugin: TokenRefreshPlugin, scope: HttpClient) {
            addAuthorizationHeader(plugin, scope)
            handleUnauthorizedResponse(plugin, scope)
        }

        private fun addAuthorizationHeader(plugin: TokenRefreshPlugin, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.State) {
                val accessToken = plugin.loginDataRepository.getAccessToken()
                accessToken?.let {
                    context.headers.append(HttpHeaders.Authorization, "Bearer $it")
                }
                proceed()
            }
        }

        private fun handleUnauthorizedResponse(plugin: TokenRefreshPlugin, scope: HttpClient) {
            scope.receivePipeline.intercept(HttpReceivePipeline.State) {
                val response = subject

                if (response.status == HttpStatusCode.Unauthorized && !isRefreshing) {
                    refreshMutex.withLock {
                        if (isRefreshing) {
                            proceed()
                        } else {
                            isRefreshing = true
                            try {
                                when (val result = plugin.refreshAccessTokenUseCase.execute()) {
                                    is VolleyResult.Success -> {
                                        val newResponse = retryRequestWithNewToken(
                                            scope,
                                            response,
                                            result.data
                                        )
                                        proceedWith(newResponse)
                                    }
                                    is VolleyResult.Failure -> {
                                        handleRefreshFailure(plugin, result.error)
                                        proceed()
                                    }
                                }
                            } finally {
                                isRefreshing = false
                            }
                        }
                    }
                } else {
                    proceed()
                }
            }
        }

        private suspend fun retryRequestWithNewToken(
            scope: HttpClient,
            response: io.ktor.client.statement.HttpResponse,
            newToken: String
        ): io.ktor.client.statement.HttpResponse {
            val originalRequest = response.call.request
            return scope.request {
                method = originalRequest.method
                url.takeFrom(originalRequest.url)
                headers.appendAll(originalRequest.headers)
                headers.remove(HttpHeaders.Authorization)
                headers.append(HttpHeaders.Authorization, "Bearer $newToken")
                setBody(originalRequest.content)
            }
        }

        private suspend fun handleRefreshFailure(plugin: TokenRefreshPlugin, error: ErrorType) {
            if (error == ErrorType.UNAUTHORIZED) {
                plugin.onUnauthorized()
            }
        }
    }
}
