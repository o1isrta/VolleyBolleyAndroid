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
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEffect.NavigateFromProfileScreen
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEffect.ShowDeleteAccountDialog
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEffect.ShowLogoutDialog
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEvent.OnDeleteAccountClick
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEvent.OnFaqClick
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEvent.OnLogoutClick
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEvent.OnPaymentsClick
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEvent.OnPersonalDataClick
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEvent.OnPlayersClick
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEvent.OnSupportClick

class ProfileScreenViewModel(
    private val deleteProfileUseCase: DeleteProfileUseCase,
) : BaseViewModel<ProfileScreenState, ProfileScreenEvent, ProfileScreenEffect>(
    initialState = ProfileScreenState
) {
    override val tag: String = ProfileScreenViewModel::class.simpleName ?: "ProfileScreenViewModel"

    override fun obtainEvent(event: ProfileScreenEvent) {
        when (event) {
            OnPlayersClick -> sendUiEffect(
                NavigateFromProfileScreen(
                    PlayersRoute
                )
            )

            OnPersonalDataClick -> sendUiEffect(
                NavigateFromProfileScreen(
                    PersonalDataRoute
                )
            )

            OnPaymentsClick -> sendUiEffect(
                NavigateFromProfileScreen(
                    PaymentsRoute
                )
            )

            OnSupportClick -> {}
            OnFaqClick -> sendUiEffect(
                NavigateFromProfileScreen(
                    FaqRoute
                )
            )

            ProfileScreenEvent.OnAboutClick -> sendUiEffect(
                NavigateFromProfileScreen(
                    AboutRoute
                )
            )

            OnLogoutClick -> {
                onLogoutClick()
            }

            OnDeleteAccountClick -> {
                onDeleteAccountClick()
            }
        }
    }

    private fun onLogoutClick() {
        sendUiEffect(
            ShowLogoutDialog(
                onPositiveButtonClick = {
                    launchSafe(
                        getErrorLogMessage = { throwable ->
                            "ProfileScreen >> Logout dialog >> YES-button: ${throwable.message}"
                        }
                    ) {
                        sendUiEffect(NavigateFromProfileScreen(LaunchRoute))
                    }
                },
                onNegativeButtonClick = { sendUiEffect(null) }
            )
        )
    }

    private fun onDeleteAccountClick() {
        sendUiEffect(
            ShowDeleteAccountDialog(
                onPositiveButtonClick = {
                    launchSafe(
                        getErrorLogMessage = { throwable ->
                            "ProfileScreen >> Delete account dialog >> YES-button: ${throwable.message}"
                        }
                    ) {
                        deleteProfileUseCase.execute()
                            .onSuccess {
                                sendUiEffect(NavigateFromProfileScreen(LaunchRoute))
                            }
                    }
                },
                onNegativeButtonClick = { sendUiEffect(null) }
            )
        )
    }
}
