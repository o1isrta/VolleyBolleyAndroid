package cy.volleybolley.registration.data.network.model

import cy.volleybolley.registration.data.dto.RegistrationRequestBody

sealed interface RegistrationRequest {
    class UserRegister(
        val path: String = "/players/register/",
        val body: RegistrationRequestBody,
    ) : RegistrationRequest
}
