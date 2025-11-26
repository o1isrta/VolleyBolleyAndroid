package cy.volleybolley.registration.data

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.profile.domain.model.PersonalData
import cy.volleybolley.registration.data.dto.toRegistrationBody
import cy.volleybolley.registration.data.network.model.RegistrationRequest
import cy.volleybolley.registration.data.network.model.RegistrationResponse
import cy.volleybolley.registration.domain.api.RegistrationRepository

class RegistrationRepositoryImpl(
    private val networkClient: NetworkClient<RegistrationRequest, RegistrationResponse>
) : RegistrationRepository {
    override suspend fun userRegistration(
        userData: PersonalData
    ): VolleyResult<Unit, ErrorType> {
        val response = networkClient.getResponse(
            RegistrationRequest.UserRegister(
                body = userData.toRegistrationBody()
            )
        )
        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }
}
