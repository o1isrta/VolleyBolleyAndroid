package cy.volleybolley.profile.presentation.ui.screens.profile

import cy.volleybolley.auth.domain.api.usecase.ClearAllLoginDataUseCase
import cy.volleybolley.core.domain.model.onSuccess
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.profile.domain.DeleteProfileUseCase
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEffect.NavigateToAbout
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEffect.NavigateToAuthorization
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEffect.NavigateToFaq
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEffect.NavigateToPayments
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEffect.NavigateToPersonalData
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEffect.NavigateToPlayers
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEffect.ShowDeleteAccountDialog
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEffect.ShowLogoutDialog
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEvent.OnDeleteAccountClick
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEvent.OnFaqClick
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEvent.OnLogoutClick
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEvent.OnPaymentsClick
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEvent.OnPersonalDataClick
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEvent.OnPlayersClick
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEvent.OnSupportClick
import kotlinx.coroutines.flow.update

class ProfileScreenViewModel(
    private val deleteProfileUseCase: DeleteProfileUseCase,
    private val clearAllLoginDataUseCase: ClearAllLoginDataUseCase
) : BaseViewModel<ProfileScreenState, ProfileScreenEvent, ProfileScreenEffect>(
    initialState = ProfileScreenState()
) {
    override val tag: String = ProfileScreenViewModel::class.simpleName ?: "ProfileScreenViewModel"

    override fun obtainEvent(event: ProfileScreenEvent) {
        when (event) {
            OnPlayersClick -> sendUiEffect(NavigateToPlayers)

            OnPersonalDataClick -> sendUiEffect(NavigateToPersonalData)

            OnPaymentsClick -> sendUiEffect(NavigateToPayments)

            OnSupportClick -> {}
            OnFaqClick -> sendUiEffect(NavigateToFaq)

            ProfileScreenEvent.OnAboutClick -> sendUiEffect(NavigateToAbout)

            OnLogoutClick -> onLogoutClick()

            OnDeleteAccountClick -> onDeleteAccountClick()
        }
    }

    private fun onLogoutClick() {
        sendUiEffect(
            ShowLogoutDialog(
                onPositiveButtonClick = { performLogout() },
                onNegativeButtonClick = { sendUiEffect(null) }
            )
        )
    }

    private fun performLogout() {
        uiStateMutable.update { it.copy(isLoading = true) }

        launchSafe(
            onError = { uiStateMutable.update { it.copy(isLoading = false) } },
            getErrorLogMessage = { throwable ->
                "ProfileScreen >> Logout: ${throwable.message}"
            }
        ) {
            clearAllLoginDataUseCase.execute()
            uiStateMutable.update { it.copy(isLoading = false) }
            sendUiEffect(NavigateToAuthorization)
        }
    }

    private fun onDeleteAccountClick() {
        sendUiEffect(
            ShowDeleteAccountDialog(
                onPositiveButtonClick = { performDeleteAccount() },
                onNegativeButtonClick = { sendUiEffect(null) }
            )
        )
    }

    private fun performDeleteAccount() {
        uiStateMutable.update { it.copy(isLoading = true) }

        launchSafe(
            onError = { uiStateMutable.update { it.copy(isLoading = false) } },
            getErrorLogMessage = { throwable ->
                "ProfileScreen >> Delete account: ${throwable.message}"
            }
        ) {
            deleteProfileUseCase.execute()
                .onSuccess {
                    clearAllLoginDataUseCase.execute()
                    uiStateMutable.update { it.copy(isLoading = false) }
                    sendUiEffect(NavigateToAuthorization)
                }
        }
    }
}
