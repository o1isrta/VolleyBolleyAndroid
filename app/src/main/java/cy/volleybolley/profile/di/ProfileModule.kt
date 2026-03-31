package cy.volleybolley.profile.di

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.di.HttpClientQualifier
import cy.volleybolley.profile.data.ProfileRepositoryImpl
import cy.volleybolley.profile.data.network.ProfileNetworkClient
import cy.volleybolley.profile.data.network.model.ProfileRequest
import cy.volleybolley.profile.data.network.model.ProfileResponse
import cy.volleybolley.profile.domain.DeleteAvatarUseCase
import cy.volleybolley.profile.domain.DeleteProfileUseCase
import cy.volleybolley.profile.domain.GetPaymentsUseCase
import cy.volleybolley.profile.domain.GetPersonalDataFromServerUseCase
import cy.volleybolley.profile.domain.UpdateAvatarUseCase
import cy.volleybolley.profile.domain.UpdatePaymentsUseCase
import cy.volleybolley.profile.domain.UpdatePersonalDataUseCase
import cy.volleybolley.profile.domain.api.ProfileRepository
import cy.volleybolley.profile.presentation.ui.screens.about.AboutScreenViewModel
import cy.volleybolley.profile.presentation.ui.screens.changephoto.ChangePhotoScreenViewModel
import cy.volleybolley.profile.presentation.ui.screens.enterpaymentdata.EnterPaymentDataScreenViewModel
import cy.volleybolley.profile.presentation.ui.screens.faq.FaqScreenViewModel
import cy.volleybolley.profile.presentation.ui.screens.payments.PaymentsScreenViewModel
import cy.volleybolley.profile.presentation.ui.screens.payments.model.BackPaymentsHolder
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenViewModel
import cy.volleybolley.profile.presentation.ui.screens.personaldata.model.BackAvatarHolder
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.PlayerProfileScreenViewModel
import cy.volleybolley.profile.presentation.ui.screens.players.PlayersScreenViewModel
import cy.volleybolley.profile.presentation.ui.screens.players.model.BackPlayerIdHolder
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenViewModel
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val profileModule = module {
    // Data
    single<NetworkClient<ProfileRequest, ProfileResponse>>(HttpClientQualifier.PROFILE.qualifier) {
        ProfileNetworkClient()
    }

    single<ProfileRepository> {
        ProfileRepositoryImpl(
            networkClient = get(named(HttpClientQualifier.PROFILE.value)),
            json = get(named(HttpClientQualifier.PROFILE.value))
        )
    }

    single<Json>(HttpClientQualifier.PROFILE.qualifier) {
        Json {
            explicitNulls = false
        }
    }

    // Domain
    factory { GetPersonalDataFromServerUseCase(repository = get()) }
    factory { GetPaymentsUseCase(repository = get()) }
    factory { UpdatePersonalDataUseCase(repository = get()) }
    factory { UpdatePaymentsUseCase(repository = get()) }
    factory { UpdateAvatarUseCase(repository = get()) }
    factory { DeleteProfileUseCase(repository = get()) }
    factory { DeleteAvatarUseCase(repository = get()) }

    // ViewModels Profile flow
    viewModel {
        ProfileScreenViewModel(
            deleteProfileUseCase = get(),
            clearAllLoginDataUseCase = get()
        )
    }
    viewModel { (backAvatarHolder: BackAvatarHolder) ->
        PersonalDataScreenViewModel(
            backAvatarHolder = backAvatarHolder,
            getPersonalDataUseCase = get(),
            updatePersonalDataUseCase = get(),
            getCountriesUseCase = get(),
            savePersonalDataUseCase = get()
        )
    }
    viewModel { AboutScreenViewModel() }
    viewModel { FaqScreenViewModel() }
    viewModel { ChangePhotoScreenViewModel(updateAvatarUseCase = get(), deleteAvatarUseCase = get()) }
    viewModel { (backPaymentsHolder: BackPaymentsHolder) ->
        PaymentsScreenViewModel(
            backPaymentsHolder = backPaymentsHolder,
            getPaymentsUseCase = get(),
            updatePaymentsUseCase = get(),
            json = get()
        )
    }
    viewModel { (paymentTypeName: String, paymentsJsonString: String) ->
        EnterPaymentDataScreenViewModel(
            updatePaymentsUseCase = get(),
            json = get(),
            paymentTypeName = paymentTypeName,
            paymentsJsonStringFromPaymentsScreen = paymentsJsonString
        )
    }
    viewModel { (backPlayerHolder: BackPlayerIdHolder) ->
        PlayersScreenViewModel(
            backPlayerIdHolder = backPlayerHolder,
        )
    }
    viewModel { (playerId: Int) ->
        PlayerProfileScreenViewModel(
            playerId = playerId,
        )
    }
}
