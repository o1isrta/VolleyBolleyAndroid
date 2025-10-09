package cy.volleybolley.games.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.model.event.Event
import cy.volleybolley.games.domain.model.event.Preview

interface GameFeedRepository {
    suspend fun getPreview(): VolleyResult<Preview, ErrorType>
    suspend fun getMyGames(): VolleyResult<List<Event>, ErrorType>
    suspend fun getInvites(): VolleyResult<List<Event>, ErrorType>
    suspend fun getArchive(): VolleyResult<List<Event>, ErrorType>
    suspend fun getUpcoming(): VolleyResult<List<Event>, ErrorType>
}
