package cy.volleybolley.courts.data.network

import cy.volleybolley.courts.data.dto.CourtDto

sealed interface CourtsResponse {
    class GetCourts(
        val courts: List<CourtDto>
    ) : CourtsResponse
}
