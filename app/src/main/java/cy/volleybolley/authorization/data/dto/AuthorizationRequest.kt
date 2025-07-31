package cy.volleybolley.authorization.data.dto

sealed interface AuthorizationRequest {
    class GoogleAuthorizationRequest(
        val path: List<String> = listOf("auth", "google", "login"),
        var body: AuthorizationRequestBody,
        ) : AuthorizationRequest
    class FacebookAuthorizationRequest(
        var path: List<String> = listOf<String>(),
        var body: AuthorizationRequestBody,
    ) : AuthorizationRequest
    class PhoneNumberAuthorizationRequest(
        var path: List<String> = listOf<String>(),
        var body: AuthorizationRequestBody,
    ) : AuthorizationRequest
}
