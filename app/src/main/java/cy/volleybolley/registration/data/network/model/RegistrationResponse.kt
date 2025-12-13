package cy.volleybolley.registration.data.network.model

sealed interface RegistrationResponse {
    object UserRegister : RegistrationResponse
}
