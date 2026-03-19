package cy.volleybolley.core.presentation.ui.screens.home.home

import android.os.Build
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyMocks
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEffect.NavigateToSearchCourt
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEffect.RequestNotificationPermission
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEvent.OnCreateNewGameClick
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEvent.OnCreateTourneyClick
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEvent.OnDonateClick
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEvent.OnFindGameClick
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEvent.OnNotificationDialogConfirm
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreenEvent.OnNotificationDialogDismiss
import cy.volleybolley.games.domain.model.event.EventType
import cy.volleybolley.notification.domain.api.permission.NotificationPermissionChecker
import kotlinx.coroutines.flow.update

class HomeScreenViewModel(
    private val notificationPermissionChecker: NotificationPermissionChecker
) : BaseViewModel<HomeScreenState, HomeScreenEvent, HomeScreenEffect>(
    initialState = HomeScreenState()
) {
    init {
        uiStateMutable.update { it.copy(nearGamesCount = 1, location = VolleyMocks.mockLocation) }
        checkNotificationPermission()
    }

    override fun obtainEvent(event: HomeScreenEvent) {
        when (event) {
            OnCreateNewGameClick -> openSearchCourt(EventType.GAME)
            OnCreateTourneyClick -> openSearchCourt(EventType.TOURNAMENT)
            OnDonateClick -> {}
            OnFindGameClick -> {}
            OnNotificationDialogConfirm -> onNotificationDialogConfirm()
            OnNotificationDialogDismiss -> onNotificationDialogDismiss()
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            !notificationPermissionChecker.isNotificationPermissionGranted()
        ) {
            uiStateMutable.update { it.copy(showNotificationPermissionDialog = true) }
        }
    }

    private fun onNotificationDialogConfirm() {
        uiStateMutable.update { it.copy(showNotificationPermissionDialog = false) }
        sendUiEffect(RequestNotificationPermission)
    }

    private fun onNotificationDialogDismiss() {
        uiStateMutable.update { it.copy(showNotificationPermissionDialog = false) }
    }

    private fun openSearchCourt(event: EventType) {
        sendUiEffect(NavigateToSearchCourt(eventType = event))
    }
}
