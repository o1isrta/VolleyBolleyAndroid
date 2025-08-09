package cy.volleybolley.notification.domain.api

interface FCMTokenStore {
    fun saveToken(token: String)
    fun getToken(): String?
}
