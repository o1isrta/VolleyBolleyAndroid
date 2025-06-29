package cy.volleybolley.core.di

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.api.NetworkClient.Companion.TIMEOUT_MILLIS
import cy.volleybolley.core.data.network.impl.KtorNetworkClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val coreModule = module {

    // NETWORK
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

            install(ContentNegotiation) {
                json(get())
            }
        }
    }

    single<NetworkClient> { KtorNetworkClient(get()) }
    // NETWORK_end

}