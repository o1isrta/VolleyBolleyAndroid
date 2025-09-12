package cy.volleybolley.authorization.data

import cy.volleybolley.authorization.data.dto.AuthorizationBody
import cy.volleybolley.authorization.data.dto.AuthorizationRequest
import cy.volleybolley.authorization.data.dto.AuthorizationResponse
import cy.volleybolley.authorization.data.dto.toData
import cy.volleybolley.authorization.data.dto.toDomain
import cy.volleybolley.authorization.data.network.AuthorizationKtorNetworkClient
import cy.volleybolley.authorization.domain.api.AuthorizationRepository
import cy.volleybolley.authorization.domain.model.AuthorizationResult
import cy.volleybolley.authorization.domain.model.AuthorizationType
import cy.volleybolley.authorization.domain.model.Player
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

class AuthorizationRepositoryImpl(
    private val client: AuthorizationKtorNetworkClient,
) : AuthorizationRepository {
    override suspend fun authorization(
        authType: AuthorizationType,
        idToken: String
    ): VolleyResult<AuthorizationResult, ErrorType> {
        val request = when (authType) {
            AuthorizationType.GOOGLE_AUTORIZATION -> {
                AuthorizationRequest.GoogleAuthorizationRequest(
                    body = AuthorizationBody(idToken = idToken)
                )
            }

            AuthorizationType.FACEBOOK_AUTORIZATION -> {
                AuthorizationRequest.FacebookAuthorizationRequest(
                    body = AuthorizationBody(idToken = idToken)
                )
            }

            AuthorizationType.PHONE_NUMBER_AUTORIZATION -> {
                AuthorizationRequest.PhoneNumberAuthorizationRequest(
                    body = AuthorizationBody(idToken = idToken)
                )
            }
        }
        val response = client.getResponse(request)
        return when (response.isSuccess) {
            true -> {
                VolleyResult.Success(
                    (response.body!! as AuthorizationResponse.AuthResponse).toDomain(),
                )
            }

            else -> {
                VolleyResult.Failure(response.resultCode.mapToErrorType())
            }
        }
    }

    override suspend fun registration(
        accessToken: String,
        registrationData: Player
    ): VolleyResult<Unit, ErrorType> {
        val response = client.getResponse(
            AuthorizationRequest.PlayerRegistrationRequest(
                accessToken = accessToken,
                body = registrationData.toData()
            )
        )
        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }
}
