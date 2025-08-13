package cy.volleybolley.core.presentation.ui.screens.profile.viewmodel

import cy.volleybolley.core.domain.model.onSuccess
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.navigation.AboutRoute
import cy.volleybolley.core.presentation.ui.navigation.FaqRoute
import cy.volleybolley.core.presentation.ui.navigation.LaunchRoute
import cy.volleybolley.core.presentation.ui.navigation.PaymentsRoute
import cy.volleybolley.core.presentation.ui.navigation.PersonalDataRoute
import cy.volleybolley.core.presentation.ui.navigation.PlayersRoute
import cy.volleybolley.core.presentation.ui.screens.profile.effect.ProfileScreenEffect
import cy.volleybolley.core.presentation.ui.screens.profile.effect.ProfileScreenEffect.NavigateOnOtherScreen
import cy.volleybolley.core.presentation.ui.screens.profile.effect.ProfileScreenEffect.ShowDeleteAccountDialog
import cy.volleybolley.core.presentation.ui.screens.profile.effect.ProfileScreenEffect.ShowLogoutDialog
import cy.volleybolley.core.presentation.ui.screens.profile.event.ProfileScreenEvent
import cy.volleybolley.core.presentation.ui.screens.profile.event.ProfileScreenEvent.OnAboutClick
import cy.volleybolley.core.presentation.ui.screens.profile.event.ProfileScreenEvent.OnDeleteAccountClick
import cy.volleybolley.core.presentation.ui.screens.profile.event.ProfileScreenEvent.OnFaqClick
import cy.volleybolley.core.presentation.ui.screens.profile.event.ProfileScreenEvent.OnLogoutClick
import cy.volleybolley.core.presentation.ui.screens.profile.event.ProfileScreenEvent.OnPaymentsClick
import cy.volleybolley.core.presentation.ui.screens.profile.event.ProfileScreenEvent.OnPersonalDataClick
import cy.volleybolley.core.presentation.ui.screens.profile.event.ProfileScreenEvent.OnPlayersClick
import cy.volleybolley.core.presentation.ui.screens.profile.event.ProfileScreenEvent.OnSupportClick
import cy.volleybolley.core.presentation.ui.screens.profile.state.ProfileScreenState
import cy.volleybolley.profile.domain.DeleteProfileUseCase

class ProfileScreenViewModel(
    private val deleteProfileUseCase: DeleteProfileUseCase,
) : BaseViewModel<ProfileScreenState, ProfileScreenEvent, ProfileScreenEffect>(
    initialState = ProfileScreenState
){
    override val tag: String = TAG

    override fun obtainEvent(event: ProfileScreenEvent) {
        when(event) {
            OnPlayersClick -> sendUiEffect(NavigateOnOtherScreen(PlayersRoute))
            OnPersonalDataClick -> sendUiEffect(NavigateOnOtherScreen(PersonalDataRoute))
            OnPaymentsClick -> sendUiEffect(NavigateOnOtherScreen(PaymentsRoute))
            OnSupportClick -> {}
            OnFaqClick -> sendUiEffect(NavigateOnOtherScreen(FaqRoute))
            OnAboutClick -> sendUiEffect(NavigateOnOtherScreen(AboutRoute))

            OnLogoutClick -> {
                sendUiEffect(ShowLogoutDialog(
                    onPositiveButtonClick = {
                        launchSafe(
                            getErrorLogMessage = {throwable ->
                                "ProfileScreen >> Logout dialog >> YES-button: ${throwable.message}"
                            }
                        ) {
                            sendUiEffect(NavigateOnOtherScreen(LaunchRoute))
                        }
                    },
                ))
            }

            OnDeleteAccountClick -> {
                sendUiEffect(ShowDeleteAccountDialog(
                    onPositiveButtonClick = {
                        launchSafe(
                            getErrorLogMessage = {throwable ->
                                "ProfileScreen >> Delete account dialog >> YES-button: ${throwable.message}"
                            }
                        ) {
                            deleteProfileUseCase.execute()
                                .onSuccess { sendUiEffect(NavigateOnOtherScreen(LaunchRoute)) }
                        }
                    },
                ))
            }
        }
    }

    companion object {
        val TAG = ProfileScreenViewModel::class.simpleName ?: "ProfileScreenViewModel"
    }
}
