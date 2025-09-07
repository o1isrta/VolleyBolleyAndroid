package cy.volleybolley.core.presentation.ui.screens.profile.playerprofile

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.core.presentation.ui.screens.profile.playerprofile.PlayerProfileScreenEffect.NavigateFromPlayerDetailScreen
import cy.volleybolley.core.presentation.ui.screens.profile.playerprofile.PlayerProfileScreenEvent.ClickOnBackFromPlayerDetails
import cy.volleybolley.core.presentation.ui.screens.profile.playerprofile.PlayerProfileScreenEvent.ClickOnFavoriteManagementButton
import cy.volleybolley.core.presentation.ui.screens.profile.playerprofile.model.PlayerDetailTemp
import kotlinx.coroutines.flow.update

class PlayerProfileScreenViewModel(
    playerId: Int,
) : BaseViewModel<PlayerProfileScreenState, PlayerProfileScreenEvent, PlayerProfileScreenEffect> (
    initialState = PlayerProfileScreenState(
        playerDetail = PlayerDetailTemp(
            id = -1,
            firstName = "",
            lastName = "",
            avatarUrl = null,
            isFavorite = false,
            level = "LIGHT",
            latestActivity = listOf()
        )
    )
) {
    private val originFavoriteStatus: Boolean

    override val tag: String = TAG

    init {
        // getPlayerDetails(playerId)
        val details = VolleyUiUtil.mockPlayerDetails.find {
            it.id == playerId
        } ?: VolleyUiUtil.mockPlayerDetails[0]
        originFavoriteStatus = details.isFavorite
        uiStateMutable.update { it.copy(playerDetail = details) }
    }

    override fun obtainEvent(event: PlayerProfileScreenEvent) {
        when (event) {
            ClickOnBackFromPlayerDetails -> sendUiEffect(
                if (uiState.value.playerDetail.isFavorite == originFavoriteStatus) {
                    NavigateFromPlayerDetailScreen(null)
                } else {
                    NavigateFromPlayerDetailScreen(uiState.value.playerDetail.id)
                }
            )

            PlayerProfileScreenEvent.ClickOnActivityMapButton -> { /*пока не ясно что тут должно быть*/ }

            is ClickOnFavoriteManagementButton -> uiStateMutable.update {
                it.copy(
                    playerDetail = it.playerDetail.copy(isFavorite = event.isFavorite)
                )
            }
        }
    }

    companion object {
        val TAG = PlayerProfileScreenViewModel::class.simpleName ?: "PlayerDetailScreenViewModel"
    }
}
