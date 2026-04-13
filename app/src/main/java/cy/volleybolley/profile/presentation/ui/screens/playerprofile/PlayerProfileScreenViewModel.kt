package cy.volleybolley.profile.presentation.ui.screens.playerprofile

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyMocks
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.PlayerProfileScreenEffect.NavigateFromPlayerDetailScreen
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.PlayerProfileScreenEvent.ClickOnActivityMapButton
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.PlayerProfileScreenEvent.ClickOnBackFromPlayerDetails
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.PlayerProfileScreenEvent.ClickOnFavoriteManagementButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update

class PlayerProfileScreenViewModel(
    playerId: Int,
) : BaseViewModel<PlayerProfileScreenState, PlayerProfileScreenEvent, PlayerProfileScreenEffect>(
    initialState = PlayerProfileScreenState.Loading
) {
    private var originFavoriteStatus: Boolean = false

    init {
        getMockDetails(playerId)
    }

    private fun getMockDetails(playerId: Int) {
        launchSafe(
            onError = {},
            getErrorLogMessage = { error -> "${error.message}" },
        ) {
            val whatStateShow = (0..1).random()
            val details = VolleyMocks.mockPlayerDetails.find {
                it.id == playerId
            } ?: VolleyMocks.mockPlayerDetails[0]
            originFavoriteStatus = details.isFavorite
            delay(1500)
            when (whatStateShow) {
                0 -> uiStateMutable.update { PlayerProfileScreenState.ShowPlayerDetails(playerDetail = details) }
                else -> uiStateMutable.update { PlayerProfileScreenState.Error }
            }
        }
    }

    override fun obtainEvent(event: PlayerProfileScreenEvent) {
        when (val currentState = uiState.value) {
            is PlayerProfileScreenState.ShowPlayerDetails -> manageEventsOnDetails(currentState, event)
            is PlayerProfileScreenState.Loading -> manageEventsOnLoadingOrError(event)
            is PlayerProfileScreenState.Error -> manageEventsOnLoadingOrError(event)
        }
    }

    private fun manageEventsOnDetails(
        state: PlayerProfileScreenState.ShowPlayerDetails,
        event: PlayerProfileScreenEvent
    ) {
        when (event) {
            ClickOnBackFromPlayerDetails -> sendUiEffect(
                if (state.playerDetail.isFavorite == originFavoriteStatus) {
                    NavigateFromPlayerDetailScreen(null)
                } else {
                    NavigateFromPlayerDetailScreen(state.playerDetail.id)
                }
            )

            ClickOnActivityMapButton -> { /*пока не ясно что тут должно быть*/ }

            is ClickOnFavoriteManagementButton -> uiStateMutable.update {
                state.copy(isLoadingFavStatus = true)
            }
        }
    }

    private fun manageEventsOnLoadingOrError(event: PlayerProfileScreenEvent) {
        when (event) {
            ClickOnBackFromPlayerDetails -> sendUiEffect(NavigateFromPlayerDetailScreen(null))
            else -> {}
        }
    }
}
