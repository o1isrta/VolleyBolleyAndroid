package cy.volleybolley.authorization.data.dto

sealed interface AuthorizationRequest {
    class GoogleAuthorizationRequest(
        val path: List<String> = listOf("auth", "google", "login"),
        var body: AuthorizationBody,
        ) : AuthorizationRequest

    class FacebookAuthorizationRequest(
        var path: List<String> = listOf(),
        var body: AuthorizationBody,
    ) : AuthorizationRequest

    class PhoneNumberAuthorizationRequest(
        var path: List<String> = listOf(),
        var body: AuthorizationBody,
    ) : AuthorizationRequest

    class PlayerRegistrationRequest(
        val accessToken: String? = null,
        var path: List<String> = listOf("players", "register"),
        var body: PlayerRegistrationBody,
    ) : AuthorizationRequest

}
