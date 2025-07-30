package cy.volleybolley.courts.data

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.courts.data.dto.toDomain
import cy.volleybolley.courts.data.network.CourtsRequest
import cy.volleybolley.courts.data.network.CourtsResponse
import cy.volleybolley.courts.domain.api.CourtsRepository
import cy.volleybolley.courts.domain.model.Court

class CourtsRepositoryImpl(
    val networkClient: NetworkClient<CourtsRequest, CourtsResponse>
) : CourtsRepository {

    override suspend fun getCourts(searchQuery: String?): VolleyResult<List<Court>, ErrorType> {
        val response = networkClient.getResponse(CourtsRequest.GetCourts(courtName = searchQuery))

        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }

        val courts = (response.body as? CourtsResponse.GetCourts)?.courts?.toDomain()
        return courts?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }
}
