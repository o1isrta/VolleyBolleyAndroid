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
        lateinit var loginDataRepository: LoginDataRepository
        lateinit var refreshAccessTokenUseCase: RefreshAccessTokenUseCase
        var onUnauthorized: (suspend () -> Unit)? = null
    }

    companion object Plugin : HttpClientPlugin<Config, TokenRefreshPlugin> {
        override val key = AttributeKey<TokenRefreshPlugin>("TokenRefreshPlugin")

        private var isRefreshing = false
        private val refreshMutex = Mutex()

        override fun prepare(block: Config.() -> Unit): TokenRefreshPlugin {
            val config = Config().apply(block)
            return TokenRefreshPlugin(
                loginDataRepository = config.loginDataRepository,
                refreshAccessTokenUseCase = config.refreshAccessTokenUseCase,
                onUnauthorized = config.onUnauthorized ?: {}
            )
        }

        override fun install(plugin: TokenRefreshPlugin, scope: HttpClient) {
            // 1. We automatically add the token to all requests.
            scope.requestPipeline.intercept(HttpRequestPipeline.State) {
                val accessToken = plugin.loginDataRepository.getAccessToken()
                accessToken?.let {
                    context.headers.append(HttpHeaders.Authorization, "Bearer $it")
                }
                proceed()
            }

            // 2. Intercepting 401 errors
            scope.receivePipeline.intercept(HttpReceivePipeline.State) {
                val response = subject

                if (response.status == HttpStatusCode.Unauthorized && !isRefreshing) {
                    // Protection against simultaneous refresh requests
                    refreshMutex.withLock {
                        if (!isRefreshing) {
                            isRefreshing = true

                            try {
                                // Trying to update the token
                                when (val result = plugin.refreshAccessTokenUseCase.execute()) {
                                    is VolleyResult.Success -> {
                                        // The token was successfully updated
                                        val newToken = result.data

                                        // Creating a new request with an updated token
                                        val originalRequest = response.call.request
                                        val newResponse = scope.request {
                                            method = originalRequest.method
                                            url.takeFrom(originalRequest.url)
                                            headers.appendAll(originalRequest.headers)
                                            headers.remove(HttpHeaders.Authorization)
                                            headers.append(HttpHeaders.Authorization, "Bearer $newToken")
                                            setBody(originalRequest.content)
                                        }

                                        // We are returning a new successful response.
                                        proceedWith(newResponse)
                                        return@withLock
                                    }
                                    is VolleyResult.Failure -> {
                                        // Refresh failed
                                        when (result.error) {
                                            ErrorType.UNAUTHORIZED -> {
                                                // Refresh token invalid → logout
                                                plugin.onUnauthorized()
                                            }
                                            else -> {
                                                // Network error or other → not logged
                                            }
                                        }
                                        // Returning the original 401 error
                                        proceed()
                                    }
                                }
                            } finally {
                                isRefreshing = false
                            }
                        } else {
                            // Another request is already updating the token → waiting
                            proceed()
                        }
                    }
                } else {
                    proceed()
                }
            }
        }
    }
}
