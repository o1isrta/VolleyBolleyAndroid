package cy.volleybolley.core.data.network.model

import kotlinx.serialization.Serializable

sealed interface ApiResponse {

    class BadResponse(
        val responseStatusCode: Int = -1,
        val message: String = "Something went wrong, dude. Shit happens..."
    ) : ApiResponse

    // Поля пока просто для примера, до уточнения API.
    @Serializable
    class AuthResponse(
        val someId: Int,
        val someToken: String,
    ): ApiResponse

}