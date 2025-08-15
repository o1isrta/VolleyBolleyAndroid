package cy.volleybolley.authorization.domain

import cy.volleybolley.authorization.domain.api.AuthorizationRepository
import cy.volleybolley.authorization.domain.api.AuthorizationUseCase
import cy.volleybolley.authorization.domain.model.AuthorizationResult
import cy.volleybolley.authorization.domain.model.AuthorizationType
import cy.volleybolley.authorization.domain.model.Player
import cy.volleybolley.core.TokensManager
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

class AuthorizationUseCaseImpl(
    val repository: AuthorizationRepository,
) : AuthorizationUseCase {

    override suspend fun authorization(
        authType: AuthorizationType,
        idToken: String
    ): VolleyResult<AuthorizationResult, ErrorType> {
        return repository.authorization(authType, idToken)
    }

    override suspend fun registration(
        accessToken: String,
        registrationData: Player
    ): VolleyResult<Unit, ErrorType> {
        return repository.registration(accessToken, registrationData)
    }

    override fun logOut() {
        TokensManager.clear()
    }


}
