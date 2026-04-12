package cy.volleybolley.profile.presentation.ui.screens.playerprofile

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyMocks
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.PlayerProfileScreenEffect.NavigateFromPlayerDetailScreen
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.PlayerProfileScreenEvent.ClickOnActivityMapButton
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.PlayerProfileScreenEvent.ClickOnBackFromPlayerDetails
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.PlayerProfileScreenEvent.ClickOnFavoriteManagementButton
import kotlinx.coroutines.flow.update

class PlayerProfileScreenViewModel(
    playerId: Int,
) : BaseViewModel<PlayerProfileScreenState, PlayerProfileScreenEvent, PlayerProfileScreenEffect>(
    initialState = PlayerProfileScreenState.ShowPlayerDetails()
) {
    private val originFavoriteStatus: Boolean

    init {
        // getPlayerDetails(playerId)
        val details = VolleyMocks.mockPlayerDetails.find {
            it.id == playerId
        } ?: VolleyMocks.mockPlayerDetails[0]
        originFavoriteStatus = details.isFavorite
        uiStateMutable.update { PlayerProfileScreenState.ShowPlayerDetails(playerDetail = details) }
    }

    override fun obtainEvent(event: PlayerProfileScreenEvent) {
        val currentState = uiState.value
        if (currentState is PlayerProfileScreenState.ShowPlayerDetails) {
            when (event) {
                ClickOnBackFromPlayerDetails -> sendUiEffect(
                    if (currentState.playerDetail.isFavorite == originFavoriteStatus) {
                        NavigateFromPlayerDetailScreen(null)
                    } else {
                        NavigateFromPlayerDetailScreen(currentState.playerDetail.id)
                    }
                )

                ClickOnActivityMapButton -> { /*пока не ясно что тут должно быть*/
                }

                is ClickOnFavoriteManagementButton -> uiStateMutable.update {
                    currentState.copy(
                        playerDetail = currentState.playerDetail.copy(isFavorite = event.isFavorite)
                    )
                }
            }
        }
    }
}
