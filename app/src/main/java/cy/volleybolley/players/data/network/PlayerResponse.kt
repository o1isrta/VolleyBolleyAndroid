package cy.volleybolley.players.data.network

import cy.volleybolley.players.data.dto.PlayerDto
import cy.volleybolley.players.data.dto.PlayerDtoDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class PlayerResponse {

    @Serializable
    data class GetAllPlayers(
        @SerialName("players") val players: List<PlayerDto>
    ) : PlayerResponse()

    @Serializable
    data class SearchPlayers(
        @SerialName("players") val players: List<PlayerDto>
    ) : PlayerResponse()

    @Serializable
    data class GetPlayerDetail(
        @SerialName("player") val player: PlayerDtoDetail
    ) : PlayerResponse()

    @Serializable
    data class AddToFavorites(
        @SerialName("player") val player: PlayerDto
    ) : PlayerResponse()

    data object RemoveFromFavorites : PlayerResponse()
}
