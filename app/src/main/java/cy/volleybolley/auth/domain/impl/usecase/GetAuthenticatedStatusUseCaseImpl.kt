package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.auth.domain.api.usecase.GetAuthenticatedStatusUseCase
import kotlinx.coroutines.flow.StateFlow

class GetAuthenticatedStatusUseCaseImpl(
    private val loginDataRepository: LoginDataRepository
) : GetAuthenticatedStatusUseCase{
    override fun execute(): StateFlow<Boolean> {
        return loginDataRepository.isAuthenticated
    }
}
