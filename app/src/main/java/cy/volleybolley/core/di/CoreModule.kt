package cy.volleybolley.core.di

import cy.volleybolley.BuildConfig
import cy.volleybolley.core.presentation.ui.screens.profile.about.AboutScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.profile.changephoto.ChangePhotoScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.profile.faq.FaqScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.profile.payments.PaymentsScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.profile.personaldata.PersonalDataScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.profile.personaldata.model.BackAvatarHolder
import cy.volleybolley.core.presentation.ui.screens.profile.profile.ProfileScreenViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModel
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

    // ViewModels Profile flow
    viewModel { ProfileScreenViewModel(deleteProfileUseCase = get()) }
    viewModel { (backAvatarHolder: BackAvatarHolder) ->
        PersonalDataScreenViewModel(
            backAvatarHolder = backAvatarHolder,
            getPersonalDataUseCase = get(),
            updatePersonalDataUseCase = get()
        )
    }
    viewModel { AboutScreenViewModel() }
    viewModel { FaqScreenViewModel() }
    viewModel { ChangePhotoScreenViewModel(updateAvatarUseCase = get(), deleteAvatarUseCase = get()) }
    viewModel { PaymentsScreenViewModel(getPaymentsUseCase = get(), updatePaymentsUseCase = get(), json = get()) }

}
