package cy.volleybolley.auth.data

import cy.volleybolley.auth.data.dto.GoogleAuthRequestBodyDto
import cy.volleybolley.auth.data.dto.UserDto
import cy.volleybolley.auth.data.dto.toDomain
import cy.volleybolley.auth.data.network.model.AuthRequest
import cy.volleybolley.auth.data.network.model.AuthResponse
import cy.volleybolley.auth.domain.api.AuthRepository
import cy.volleybolley.auth.domain.models.LoginData
import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil.showDebugLog

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

        val userDto = (response.body as? AuthResponse.GoogleResponse)?.playerUser
        val loginData = (response.body as? AuthResponse.GoogleResponse)?.toDomain()

        showUserDtoLog(userDto)

        return loginData?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    private fun showUserDtoLog(userDto: UserDto?) {
        showDebugLog(TAG, "USER_DTO section start =========================")
        showDebugLog(TAG, "playerId = ${userDto?.playerId}")
        showDebugLog(TAG, "isRegistered = ${userDto?.isRegistered}")
        showDebugLog(TAG, "name = ${userDto?.firstName}")
        showDebugLog(TAG, "lastName = ${userDto?.lastName}")
        showDebugLog(TAG, "avatar = ${userDto?.avatar}")
        showDebugLog(TAG, "gender = ${userDto?.gender}")
        showDebugLog(TAG, "level = ${userDto?.level}")
        showDebugLog(TAG, "dateOfBirth = ${userDto?.dateOfBirth}")
        showDebugLog(TAG, "country = ${userDto?.country}")
        showDebugLog(TAG, "city = ${userDto?.city}")
        showDebugLog(TAG, "USER_DTO section end =========================")
    }

    companion object {
        const val TAG = "AuthRepositoryImpl"
    }
}
