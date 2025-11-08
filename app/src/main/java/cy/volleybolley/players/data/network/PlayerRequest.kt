package cy.volleybolley.players.data.network

sealed class PlayerRequest(
    val path: String
) {
    class GetAllPlayers : PlayerRequest(
        path = "players"
    )

    class SearchPlayers(val name: String) : PlayerRequest(
        path = "players"
    )

    class GetPlayerDetail(val playerId: Int) : PlayerRequest(
        path = "players/$playerId"
    )

    class AddToFavorites(val playerId: Int) : PlayerRequest(
        path = "players/$playerId/favorite"
    )

    class RemoveFromFavorites(val playerId: Int) : PlayerRequest(
        path = "players/$playerId/favorite"
    )
}
