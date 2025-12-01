package cy.volleybolley.core.di

import cy.volleybolley.BuildConfig
import cy.volleybolley.core.presentation.App
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenViewModel
import cy.volleybolley.success.SucceedGame
import cy.volleybolley.success.SuccessViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

const val TIMEOUT_MILLIS = 30_000L

val coreModule = module {

    single<Json> {
        Json {
            isLenient = true
            ignoreUnknownKeys = true
            prettyPrint = true
        }
    }

    single<HttpClient> {
        HttpClient(OkHttp) {
            install(HttpTimeout) {
                connectTimeoutMillis = TIMEOUT_MILLIS
                requestTimeoutMillis = TIMEOUT_MILLIS
                socketTimeoutMillis = TIMEOUT_MILLIS
            }

            if (BuildConfig.DEBUG) {
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.ALL
                }
            }

            install(ContentNegotiation) {
                json(get())
            }
        }
    }

    single<CoroutineScope> {
        (androidContext() as App).applicationScope
    }

    viewModel { (event: SucceedGame) ->
        SuccessViewModel(
            createdEvent = event
        )
    }
    viewModel { HomeScreenViewModel() }
}
