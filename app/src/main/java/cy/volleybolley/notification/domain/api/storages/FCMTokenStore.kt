package cy.volleybolley.notification.domain.api.storages

interface FCMTokenStore {
    fun saveToken(token: String)
    fun getToken(): String?
}
