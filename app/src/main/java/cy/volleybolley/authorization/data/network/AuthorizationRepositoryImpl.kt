package cy.volleybolley.authorization.data.network

import cy.volleybolley.authorization.data.dto.AuthorizationRequest.*
import cy.volleybolley.authorization.data.dto.AuthorizationRequestBody
import cy.volleybolley.authorization.data.dto.AuthorizationResponse
import cy.volleybolley.authorization.data.toAuthorizationResult
import cy.volleybolley.authorization.data.toPlayerRegistrationBody
import cy.volleybolley.authorization.domain.api.AuthorizationRepository
import cy.volleybolley.authorization.domain.model.AuthorizationResult
import cy.volleybolley.authorization.domain.model.AuthorizationType
import cy.volleybolley.authorization.domain.model.RegistrationData
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
                GoogleAuthorizationRequest(
                    body = AuthorizationRequestBody(idToken = idToken)
                )
            }
            AuthorizationType.FACEBOOK_AUTORIZATION -> {
                FacebookAuthorizationRequest(
                    body = AuthorizationRequestBody(idToken = idToken)
                )
            }
            AuthorizationType.PHONE_NUVBER_AUTORIZATION -> {
                PhoneNumberAuthorizationRequest(
                    body = AuthorizationRequestBody(idToken = idToken)
                )
            }
        }
        val response = client.getResponse(request)
        return when(response.isSuccess) {
            true -> {
                VolleyResult.Success(
                    (response.body!! as AuthorizationResponse.AuthResponse).toAuthorizationResult(),
                )
            }
            else -> {
                VolleyResult.Failure(response.resultCode.mapToErrorType())
            }
        }
    }

    override suspend fun registration(
        accessToken: String?,
        registrationData: RegistrationData
    ): VolleyResult<Unit, ErrorType> {
        val response = client.getResponse(
            PlayerRegistrationRequest(
                accessToken = accessToken,
                body = registrationData.toPlayerRegistrationBody()
            )
        )
        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }


}
