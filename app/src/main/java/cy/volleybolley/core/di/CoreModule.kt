package cy.volleybolley.core.di

import cy.volleybolley.BuildConfig
import cy.volleybolley.auth.data.network.model.AuthRequest
import cy.volleybolley.auth.domain.api.AuthRepository
import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.auth.domain.api.storage.TokenStorage
import cy.volleybolley.core.ResourceProvider
import cy.volleybolley.core.ResourceProviderImpl
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.presentation.App
import cy.volleybolley.core.presentation.MainActivityViewModel
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenViewModel
import cy.volleybolley.referencedata.data.network.ReferenceDataRequest
import cy.volleybolley.success.SucceedGame
import cy.volleybolley.success.SuccessViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

const val TIMEOUT_MILLIS = 30_000L

private fun isRequestRequireAuthorization(request: HttpRequestBuilder): Boolean {
    val path = request.url.buildString().substringAfter(BuildConfig.BASE_URL.dropLast(1))
    val endpoints = listOf(
        ReferenceDataRequest.CurrencyRequest.PATH,
        ReferenceDataRequest.CountriesRequest.PATH,
        AuthRequest.Google.PATH,
        AuthRequest.Phone.PATH,
        AuthRequest.RefreshAccessToken.PATH
    )
    for (endpoint in endpoints) {
        if (path == endpoint) {
            return false
        }
    }
    return true
}

val coreModule = module {

    single<Json> {
        Json {
            isLenient = true
            ignoreUnknownKeys = true
            prettyPrint = true
        }
    }

    // Main HttpClient with Auth plugin for token refresh (for all modules except Auth)
    single<HttpClient> {
        HttpClient(OkHttp) {
            install(HttpTimeout) {
                connectTimeoutMillis = TIMEOUT_MILLIS
                requestTimeoutMillis = TIMEOUT_MILLIS
                socketTimeoutMillis = TIMEOUT_MILLIS
            }

            if (BuildConfig.IS_LOG_ENABLED) {
                install(Logging) {
                    logger = Logger.ANDROID
                    level = LogLevel.ALL
                }
            }

            install(ContentNegotiation) {
                json(get())
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        val loginDataRepo = get<LoginDataRepository>()
                        val accessToken = loginDataRepo.getAccessToken()
                        val refreshToken = loginDataRepo.getRefreshToken()
                        if (accessToken != null && refreshToken != null) {
                            BearerTokens(accessToken, refreshToken)
                        } else {
                            null
                        }
                    }

                    refreshTokens {
                        val tokenStorage = get<TokenStorage>()
                        val refreshToken = tokenStorage.getRefreshToken()

                        if (refreshToken == null) {
                            get<LoginDataRepository>().clearAll()
                            null
                        } else {
                            when (val result = get<AuthRepository>().refreshAccessToken(refreshToken)) {
                                is VolleyResult.Success -> {
                                    tokenStorage.saveAccessToken(result.data)
                                    BearerTokens(result.data, refreshToken)
                                }
                                is VolleyResult.Failure -> {
                                    get<LoginDataRepository>().clearAll()
                                    null
                                }
                            }
                        }
                    }

                    sendWithoutRequest { request: HttpRequestBuilder ->
                        isRequestRequireAuthorization(request)
                    }
                }
            }
        }
    }

    single<CoroutineScope> {
        (androidContext() as App).applicationScope
    }

    viewModel {
        MainActivityViewModel(
            sendDeviceTokenUseCase = get(),
            fcmTokenStore = get(),
            notificationPermissionChecker = get(),
            getAuthenticatedStatusUseCase = get(),
            getPersonalDataUseCase = get(),
            checkRefreshTokenExpirationUseCase = get(),
            clearAllLoginDataUseCase = get()
        )
    }
    viewModel { HomeScreenViewModel(notificationPermissionChecker = get()) }
    viewModel { (event: SucceedGame) ->
        SuccessViewModel(
            createdEvent = event
        )
    }
    single<ResourceProvider> { ResourceProviderImpl(androidContext()) }
}
