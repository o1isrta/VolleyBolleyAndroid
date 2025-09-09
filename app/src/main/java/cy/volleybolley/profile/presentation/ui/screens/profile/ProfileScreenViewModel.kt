package cy.volleybolley.profile.presentation.ui.screens.profile

import cy.volleybolley.core.domain.model.onSuccess
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.navigation.AboutRoute
import cy.volleybolley.core.presentation.ui.navigation.FaqRoute
import cy.volleybolley.core.presentation.ui.navigation.LaunchRoute
import cy.volleybolley.core.presentation.ui.navigation.PaymentsRoute
import cy.volleybolley.core.presentation.ui.navigation.PersonalDataRoute
import cy.volleybolley.core.presentation.ui.navigation.PlayersRoute
import cy.volleybolley.profile.domain.DeleteProfileUseCase

class ProfileScreenViewModel(
    private val deleteProfileUseCase: DeleteProfileUseCase,
) : BaseViewModel<ProfileScreenState, ProfileScreenEvent, ProfileScreenEffect>(
    initialState = ProfileScreenState
) {
    override val tag: String = TAG

    override fun obtainEvent(event: ProfileScreenEvent) {
        when (event) {
            ProfileScreenEvent.OnPlayersClick -> sendUiEffect(
                ProfileScreenEffect.NavigateFromProfileScreen(
                    PlayersRoute
                )
            )

            ProfileScreenEvent.OnPersonalDataClick -> sendUiEffect(
                ProfileScreenEffect.NavigateFromProfileScreen(
                    PersonalDataRoute
                )
            )

            ProfileScreenEvent.OnPaymentsClick -> sendUiEffect(
                ProfileScreenEffect.NavigateFromProfileScreen(
                    PaymentsRoute
                )
            )

            ProfileScreenEvent.OnSupportClick -> {}
            ProfileScreenEvent.OnFaqClick -> sendUiEffect(
                ProfileScreenEffect.NavigateFromProfileScreen(
                    FaqRoute
                )
            )

            ProfileScreenEvent.OnAboutClick -> sendUiEffect(
                ProfileScreenEffect.NavigateFromProfileScreen(
                    AboutRoute
                )
            )

            ProfileScreenEvent.OnLogoutClick -> { onLogoutClick() }

            ProfileScreenEvent.OnDeleteAccountClick -> { onDeleteAccountClick() }
        }
    }

    private fun onLogoutClick() {
        sendUiEffect(
            ProfileScreenEffect.ShowLogoutDialog(
                onPositiveButtonClick = {
                    launchSafe(
                        getErrorLogMessage = { throwable ->
                            "ProfileScreen >> Logout dialog >> YES-button: ${throwable.message}"
                        }
                    ) {
                        sendUiEffect(ProfileScreenEffect.NavigateFromProfileScreen(LaunchRoute))
                    }
                },
            )
        )
    }

    private fun onDeleteAccountClick() {
        sendUiEffect(
            ProfileScreenEffect.ShowDeleteAccountDialog(
                onPositiveButtonClick = {
                    launchSafe(
                        getErrorLogMessage = { throwable ->
                            "ProfileScreen >> Delete account dialog >> YES-button: ${throwable.message}"
                        }
                    ) {
                        deleteProfileUseCase.execute()
                            .onSuccess {
                                sendUiEffect(ProfileScreenEffect.NavigateFromProfileScreen(LaunchRoute))
                            }
                    }
                },
            )
        )
    }

    companion object {
        val TAG = ProfileScreenViewModel::class.simpleName ?: "ProfileScreenViewModel"
    }
}
