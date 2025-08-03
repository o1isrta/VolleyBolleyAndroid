package cy.volleybolley.profile.data.network.model

sealed interface ProfileResponse {
    class GetPersonalData() : ProfileResponse
    class GetPayments() : ProfileResponse
    class UpdateProfileAvatar() : ProfileResponse
    class UpdatePersonalData() : ProfileResponse
    class UpdatePayments() : ProfileResponse
    class DeleteProfile() : ProfileResponse
}
