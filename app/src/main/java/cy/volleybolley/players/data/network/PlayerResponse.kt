package cy.volleybolley.players.data.network

import cy.volleybolley.players.data.dto.PlayerDto
import cy.volleybolley.players.data.dto.PlayerDetailDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface PlayerResponse {
    class GetAllPlayers(
        val players: List<PlayerDto>
    ) : PlayerResponse

    @Serializable
    class GetPlayerDetail(
        @SerialName("player") val player: PlayerDetailDto
    ) : PlayerResponse

    object AddToFavorites : PlayerResponse

    object RemoveFromFavorites : PlayerResponse
}
