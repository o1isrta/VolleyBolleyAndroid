package cy.volleybolley.courts.data.network

import cy.volleybolley.courts.data.dto.CourtDto
import kotlinx.serialization.Serializable

sealed interface CourtsResponse {
    @Serializable
    class GetCourtsResponse(
        val courts: List<CourtDto>
    ): CourtsResponse
}