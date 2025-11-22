package cy.volleybolley.auth.domain.api.usecase

import kotlinx.coroutines.flow.StateFlow

interface GetAuthenticatedStatusUseCase {
    fun execute(): StateFlow<Boolean>
}
