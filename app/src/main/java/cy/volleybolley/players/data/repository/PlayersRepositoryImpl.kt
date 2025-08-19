package cy.volleybolley.players.data.repository

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.players.data.mapper.toDomain
import cy.volleybolley.players.data.network.PlayerRequest
import cy.volleybolley.players.data.network.PlayerResponse
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.players.domain.model.PlayerDetail
import cy.volleybolley.players.domain.repository.PlayersRepository

class PlayersRepositoryImpl(
    private val networkClient: NetworkClient<PlayerRequest, PlayerResponse>
) : PlayersRepository {

    override suspend fun getAllPlayers(): VolleyResult<List<Player>, ErrorType> {
        val response = networkClient.getResponse(PlayerRequest.GetAllPlayers)
        if (!response.isSuccess) return VolleyResult.Failure(mapError(response.resultCode))

        val body = response.body as? PlayerResponse.GetAllPlayers
            ?: return VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)

        return VolleyResult.Success(body.players.map { it.toDomain() })
    }

    override suspend fun searchPlayers(query: String): VolleyResult<List<Player>, ErrorType> {
        val response = networkClient.getResponse(PlayerRequest.SearchPlayers(query))
        if (!response.isSuccess) return VolleyResult.Failure(mapError(response.resultCode))

        val body = response.body as? PlayerResponse.SearchPlayers
            ?: return VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)

        return VolleyResult.Success(body.players.map { it.toDomain() })
    }

    override suspend fun getPlayerDetail(playerId: Int): VolleyResult<PlayerDetail, ErrorType> {
        val response = networkClient.getResponse(PlayerRequest.GetPlayerDetail(playerId))
        if (!response.isSuccess) return VolleyResult.Failure(mapError(response.resultCode))

        val body = response.body as? PlayerResponse.GetPlayerDetail
            ?: return VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)

        return VolleyResult.Success(body.player.toDomain())
    }

    override suspend fun addToFavorites(playerId: Int): VolleyResult<Player, ErrorType> {
        val response = networkClient.getResponse(PlayerRequest.AddToFavorites(playerId))
        if (!response.isSuccess) return VolleyResult.Failure(mapError(response.resultCode))

        val body = response.body as? PlayerResponse.AddToFavorites
            ?: return VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)

        return VolleyResult.Success(body.player.toDomain())
    }

    override suspend fun removeFromFavorites(playerId: Int): VolleyResult<Unit, ErrorType> {
        val response = networkClient.getResponse(PlayerRequest.RemoveFromFavorites(playerId))
        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(mapError(response.resultCode))
        }
    }

    private fun mapError(resultCodeObj: Any?): ErrorType {
        val code = when (resultCodeObj) {
            is Int -> resultCodeObj
            is Number -> resultCodeObj.toInt()
            else -> {
                try {
                    val cls = resultCodeObj?.javaClass ?: return ErrorType.UNKNOWN_ERROR
                    val names = listOf("value", "code", "status", "statusCode")
                    val field = names.mapNotNull { n ->
                        try {
                            cls.getDeclaredField(n)
                        } catch (_: Throwable) {
                            null
                        }
                    }.firstOrNull()
                    if (field != null) {
                        field.isAccessible = true
                        val v = field.get(resultCodeObj)
                        (v as? Number)?.toInt()
                    } else {
                        null
                    }
                } catch (_: Throwable) {
                    null
                }
            }
        }

        return when {
            code == null -> ErrorType.UNKNOWN_ERROR
            code in 400..499 -> when (code) {
                400 -> ErrorType.BAD_REQUEST
                404 -> ErrorType.NOT_FOUND
                else -> ErrorType.UNKNOWN_ERROR
            }

            code >= 500 -> ErrorType.SERVER_ERROR
            else -> ErrorType.UNKNOWN_ERROR
        }
    }
}
