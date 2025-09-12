package cy.volleybolley.authorization.domain.api

import cy.volleybolley.authorization.domain.model.AuthorizationResult
import cy.volleybolley.authorization.domain.model.AuthorizationType
import cy.volleybolley.authorization.domain.model.Player
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

interface AuthorizationRepository {
    suspend fun authorization(
        authType: AuthorizationType,
        idToken: String
    ): VolleyResult<AuthorizationResult, ErrorType>

    suspend fun registration(accessToken: String, registrationData: Player): VolleyResult<Unit, ErrorType>
}
