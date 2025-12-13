package cy.volleybolley.auth.domain.impl.usecase

import cy.volleybolley.auth.domain.api.usecase.CheckRefreshTokenExpirationUseCase
import cy.volleybolley.auth.domain.api.usecase.GetRefreshTokenTimestampUseCase
import java.util.concurrent.TimeUnit

class CheckRefreshTokenExpirationUseCaseImpl(
    private val getRefreshTokenTimestampUseCase: GetRefreshTokenTimestampUseCase
) : CheckRefreshTokenExpirationUseCase {
    override suspend fun execute(): Boolean {
        val timestamp = getRefreshTokenTimestampUseCase.execute() ?: return false
        val elapsedTime = System.currentTimeMillis() - timestamp
        val remainingTime = REFRESH_TOKEN_LIFETIME_MILLIS - elapsedTime
        return remainingTime < EXPIRATION_THRESHOLD_MILLIS
    }

    companion object {
        private val REFRESH_TOKEN_LIFETIME_MILLIS = TimeUnit.DAYS.toMillis(30)
        private val EXPIRATION_THRESHOLD_MILLIS = TimeUnit.DAYS.toMillis(1)
    }
}
