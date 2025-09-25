package cy.volleybolley.players.data.network

sealed class PlayerRequest(
    val path: String,
    val authToken: String
) {
    class GetAllPlayers(authToken: String) : PlayerRequest(
        path = "players",
        authToken = authToken
    )

    class SearchPlayers(val name: String, authToken: String) : PlayerRequest(
        path = "players",
        authToken = authToken
    )

    class GetPlayerDetail(val playerId: Int, authToken: String) : PlayerRequest(
        path = "players/$playerId",
        authToken = authToken
    )

    class AddToFavorites(val playerId: Int, authToken: String) : PlayerRequest(
        path = "players/$playerId/favorite",
        authToken = authToken
    )

    class RemoveFromFavorites(val playerId: Int, authToken: String) : PlayerRequest(
        path = "players/$playerId/favorite",
        authToken = authToken
    )
}
