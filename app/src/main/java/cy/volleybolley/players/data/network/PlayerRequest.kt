package cy.volleybolley.players.data.network

sealed class PlayerRequest(
    val path: String
) {
    class GetAllPlayers : PlayerRequest(
        path = PLAYERS
    )

    class GetPlayerDetail(val playerId: Int) : PlayerRequest(
        path = "${PLAYERS}$playerId/"
    )

    class AddToFavorites(val playerId: Int) : PlayerRequest(
        path = "${PLAYERS}$playerId${FAVORITE}"
    )

    class RemoveFromFavorites(val playerId: Int) : PlayerRequest(
        path = "${PLAYERS}$playerId${FAVORITE}"
    )
}

private const val PLAYERS = "/players/"
private const val FAVORITE = "/favorite/"
