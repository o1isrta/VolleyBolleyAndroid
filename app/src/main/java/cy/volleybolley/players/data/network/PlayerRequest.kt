package cy.volleybolley.players.data.network

sealed class PlayerRequest(
    val path: String,
    val authToken: String? = null
) {
    class GetAllPlayers(
        authToken: String? = null
    ) : PlayerRequest(
        path = "players",
        authToken = authToken
    )

    class SearchPlayers(
        val name: String,
        authToken: String? = null
    ) : PlayerRequest(
        path = "players",
        authToken = authToken
    )

    class GetPlayerDetail(
        val playerId: Int,
        authToken: String? = null
    ) : PlayerRequest(
        path = "players/$playerId",
        authToken = authToken
    )

    class AddToFavorites(
        val playerId: Int,
        authToken: String? = null
    ) : PlayerRequest(
        path = "players/$playerId/favorite",
        authToken = authToken
    )

    class RemoveFromFavorites(
        val playerId: Int,
        authToken: String? = null
    ) : PlayerRequest(
        path = "players/$playerId/favorite",
        authToken = authToken
    )
}
