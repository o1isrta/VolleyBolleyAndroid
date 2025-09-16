package cy.volleybolley.players.data.network

import cy.volleybolley.players.data.dto.PlayerDto
import cy.volleybolley.players.data.dto.PlayerDtoDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface PlayerResponse {

    data class GetAllPlayers(
        val players: List<PlayerDto>
    ) : PlayerResponse

    data class SearchPlayers(
        val players: List<PlayerDto>
    ) : PlayerResponse

    @Serializable
    data class GetPlayerDetail(
        @SerialName("player") val player: PlayerDtoDetail
    ) : PlayerResponse

    data class AddToFavorites(
        val player: PlayerDto
    ) : PlayerResponse

    data object RemoveFromFavorites : PlayerResponse
}
