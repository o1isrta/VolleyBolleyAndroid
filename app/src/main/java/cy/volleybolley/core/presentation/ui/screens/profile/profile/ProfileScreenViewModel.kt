package cy.volleybolley.core.presentation.ui.screens.profile.profile

import cy.volleybolley.core.domain.model.onSuccess
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.navigation.AboutRoute
import cy.volleybolley.core.presentation.ui.navigation.FaqRoute
import cy.volleybolley.core.presentation.ui.navigation.LaunchRoute
import cy.volleybolley.core.presentation.ui.navigation.PaymentsRoute
import cy.volleybolley.core.presentation.ui.navigation.PersonalDataRoute
import cy.volleybolley.core.presentation.ui.navigation.PlayersRoute
import cy.volleybolley.core.presentation.ui.screens.profile.profile.ProfileScreenEffect.NavigateFromProfileScreen
import cy.volleybolley.core.presentation.ui.screens.profile.profile.ProfileScreenEffect.ShowDeleteAccountDialog
import cy.volleybolley.core.presentation.ui.screens.profile.profile.ProfileScreenEffect.ShowLogoutDialog
import cy.volleybolley.core.presentation.ui.screens.profile.profile.ProfileScreenEvent.OnAboutClick
import cy.volleybolley.core.presentation.ui.screens.profile.profile.ProfileScreenEvent.OnDeleteAccountClick
import cy.volleybolley.core.presentation.ui.screens.profile.profile.ProfileScreenEvent.OnFaqClick
import cy.volleybolley.core.presentation.ui.screens.profile.profile.ProfileScreenEvent.OnLogoutClick
import cy.volleybolley.core.presentation.ui.screens.profile.profile.ProfileScreenEvent.OnPaymentsClick
import cy.volleybolley.core.presentation.ui.screens.profile.profile.ProfileScreenEvent.OnPersonalDataClick
import cy.volleybolley.core.presentation.ui.screens.profile.profile.ProfileScreenEvent.OnPlayersClick
import cy.volleybolley.core.presentation.ui.screens.profile.profile.ProfileScreenEvent.OnSupportClick
import cy.volleybolley.profile.domain.DeleteProfileUseCase

class ProfileScreenViewModel(
    private val deleteProfileUseCase: DeleteProfileUseCase,
) : BaseViewModel<ProfileScreenState, ProfileScreenEvent, ProfileScreenEffect>(
    initialState = ProfileScreenState
){
    override val tag: String = TAG

    override fun obtainEvent(event: ProfileScreenEvent) {
        when(event) {
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
            OnAboutClick -> sendUiEffect(
                NavigateFromProfileScreen(
                    AboutRoute
                )
            )

            OnLogoutClick -> {
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
                    )
                )
            }

            OnDeleteAccountClick -> {
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
                    )
                )
            }
        }
    }

    companion object {
        val TAG = ProfileScreenViewModel::class.simpleName ?: "ProfileScreenViewModel"
    }
}
