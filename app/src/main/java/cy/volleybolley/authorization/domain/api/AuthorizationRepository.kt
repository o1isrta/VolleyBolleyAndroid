package cy.volleybolley.authorization.domain.api

import cy.volleybolley.authorization.domain.model.AuthorizationResult
import cy.volleybolley.authorization.domain.model.AuthorizationType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

interface AuthorizationRepository {
    suspend fun authorize(authType: AuthorizationType, idToken: String): VolleyResult<AuthorizationResult, ErrorType>
}
