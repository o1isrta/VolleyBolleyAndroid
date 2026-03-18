package cy.volleybolley.auth.data

import cy.volleybolley.auth.data.dto.AuthRequestBodyDto
import cy.volleybolley.auth.data.dto.RefreshAccessTokenRequestBodyDto
import cy.volleybolley.auth.data.dto.toDomain
import cy.volleybolley.auth.data.network.model.AuthRequest
import cy.volleybolley.auth.data.network.model.AuthResponse
import cy.volleybolley.auth.domain.api.AuthRepository
import cy.volleybolley.auth.domain.models.LoginData
import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

class AuthRepositoryImpl(private val networkClient: NetworkClient<AuthRequest, AuthResponse>) : AuthRepository {
    override suspend fun loginWithGoogle(idToken: String): VolleyResult<LoginData, ErrorType> {
        val response = networkClient.getResponse(
            AuthRequest.Google(
                body = AuthRequestBodyDto(idToken)
            )
        )
        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
        val loginData = (response.body as? AuthResponse.GoogleResponse)?.toDomain()

        return loginData?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun refreshAccessToken(refreshToken: String): VolleyResult<String, ErrorType> {
        val response = networkClient.getResponse(
            AuthRequest.RefreshAccessToken(
                body = RefreshAccessTokenRequestBodyDto(refreshToken)
            )
        )

        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }

        val accessToken = (response.body as? AuthResponse.RefreshAccessTokenResponse)?.accessToken
        return accessToken?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun loginWithPhone(idToken: String): VolleyResult<LoginData, ErrorType> {
        val response = networkClient.getResponse(
            AuthRequest.Phone(
                body = AuthRequestBodyDto(idToken)
            )
        )

        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())

        }

        val loginData = (response.body as? AuthResponse.GoogleResponse)?.toDomain()

        return loginData?.
            let { VolleyResult.Success(it) }
            ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    private companion object {
        val TAG = AuthRepository::class.simpleName.orEmpty()
    }
}
