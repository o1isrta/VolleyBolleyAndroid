package cy.volleybolley.core.presentation.ui.screens.profile.playerprofile

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
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
    override val tag: String = TAG

    init {
        // getPlayerDetails(playerId)
        _uiState.update {
            it.copy(
                playerDetail = VolleyUiUtil.mockPlayerDetails.find {
                    it.id == playerId
                } ?: VolleyUiUtil.mockPlayerDetails[0]
            )
        }
    }

    override fun obtainEvent(event: PlayerProfileScreenEvent) {

    }

    companion object {
        val TAG = PlayerProfileScreenViewModel::class.simpleName ?: "PlayerDetailScreenViewModel"
    }
}
