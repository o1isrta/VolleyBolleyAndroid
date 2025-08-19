package cy.volleybolley.players.data.network

sealed class PlayerRequest(val path: String) {

    data object GetAllPlayers : PlayerRequest("players")

    data class SearchPlayers(val name: String) : PlayerRequest("players")

    data class GetPlayerDetail(val playerId: Int) : PlayerRequest("players/$playerId")

    data class AddToFavorites(val playerId: Int) : PlayerRequest("players/$playerId/favorite")

    data class RemoveFromFavorites(val playerId: Int) : PlayerRequest("players/$playerId/favorite")
}
