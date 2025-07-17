package cy.volleybolley.authorization.domain.api

import cy.volleybolley.authorization.domain.model.AuthorizationResult

interface AuthorizationRepository {
    suspend fun authorize(idToken: String): AuthorizationResult
}