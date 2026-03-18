package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.state.AuthStateHolder
import cy.volleybolley.auth.domain.api.usecase.GetAuthenticatedStatusUseCase
import kotlinx.coroutines.flow.StateFlow

class GetAuthenticatedStatusUseCaseImpl(
    private val authStateHolder: AuthStateHolder
) : GetAuthenticatedStatusUseCase {
    override fun execute(): StateFlow<Boolean> {
        return authStateHolder.isAuthenticated
    }
}
