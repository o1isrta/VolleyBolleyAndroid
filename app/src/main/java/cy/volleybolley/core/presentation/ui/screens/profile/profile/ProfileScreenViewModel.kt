package cy.volleybolley.core.presentation.ui.screens.profile.profile

import cy.volleybolley.core.domain.model.onSuccess
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.navigation.AboutRoute
import cy.volleybolley.core.presentation.ui.navigation.FaqRoute
import cy.volleybolley.core.presentation.ui.navigation.LaunchRoute
import cy.volleybolley.core.presentation.ui.navigation.PaymentsRoute
import cy.volleybolley.core.presentation.ui.navigation.PersonalDataRoute
import cy.volleybolley.core.presentation.ui.navigation.PlayersRoute
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
                ProfileScreenEffect.NavigateFromProfileScreen(
                    PlayersRoute
                )
            )
            OnPersonalDataClick -> sendUiEffect(
                ProfileScreenEffect.NavigateFromProfileScreen(
                    PersonalDataRoute
                )
            )
            OnPaymentsClick -> sendUiEffect(
                ProfileScreenEffect.NavigateFromProfileScreen(
                    PaymentsRoute
                )
            )
            OnSupportClick -> {}
            OnFaqClick -> sendUiEffect(
                ProfileScreenEffect.NavigateFromProfileScreen(
                    FaqRoute
                )
            )
            OnAboutClick -> sendUiEffect(
                ProfileScreenEffect.NavigateFromProfileScreen(
                    AboutRoute
                )
            )

            OnLogoutClick -> {
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

            OnDeleteAccountClick -> {
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
                                        sendUiEffect(
                                            ProfileScreenEffect.NavigateFromProfileScreen(
                                                LaunchRoute
                                            )
                                        )
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
