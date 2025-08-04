package cy.volleybolley.core.data.network.model

sealed class PlayerRequest {
    data object GetAllPlayers : PlayerRequest()
    data class SearchPlayers(val query: String) : PlayerRequest()
    data class GetPlayerDetail(val playerId: Int) : PlayerRequest()
    data class AddToFavorites(val playerId: Int) : PlayerRequest()
    data class RemoveFromFavorites(val playerId: Int) : PlayerRequest()
}

