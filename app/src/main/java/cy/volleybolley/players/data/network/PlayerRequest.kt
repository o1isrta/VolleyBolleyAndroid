package cy.volleybolley.players.data.network

sealed class PlayerRequest(val path: String, val authToken: String? = null) {
    class GetAllPlayers(authToken: String? = null) : PlayerRequest("players", authToken)
    class SearchPlayers(val name: String, authToken: String? = null) : PlayerRequest("players", authToken)
    class GetPlayerDetail(val playerId: Int, authToken: String? = null) : PlayerRequest("players/$playerId", authToken)
    class AddToFavorites(val playerId: Int, authToken: String? = null) : PlayerRequest("players/$playerId/favorite", authToken)
    class RemoveFromFavorites(val playerId: Int, authToken: String? = null) : PlayerRequest("players/$playerId/favorite", authToken)
}
