package cy.volleybolley.auth.data

import cy.volleybolley.auth.data.dto.GoogleAuthRequestBodyDto
import cy.volleybolley.auth.data.dto.toDomain
import cy.volleybolley.auth.data.network.model.AuthRequest
import cy.volleybolley.auth.data.network.model.AuthResponse
import cy.volleybolley.auth.domain.AuthRepository
import cy.volleybolley.auth.domain.models.LoginData
import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

class AuthRepositoryImpl(private val networkClient: NetworkClient<AuthRequest, AuthResponse>) : AuthRepository {
    override suspend fun loginWithGoogle(idToken: String): VolleyResult<LoginData, ErrorType> {
        val response = networkClient.getResponse(
            AuthRequest.Google(
                body = GoogleAuthRequestBodyDto(idToken)
            )
        )

        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }

        val loginData = (response.body as? AuthResponse.GoogleResponse)?.toDomain()
        return loginData?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }
}
