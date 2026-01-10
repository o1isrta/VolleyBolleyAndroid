package cy.volleybolley.auth.domain.api.usecase

interface CheckRefreshTokenExpirationUseCase {
    /**
     * Проверяет, осталось ли меньше 24 часов до истечения refresh token
     * @return true если осталось меньше 24 часов (нужен logout)
     */
    suspend fun execute(): Boolean
}
