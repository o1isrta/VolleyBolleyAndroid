package cy.volleybolley.profile.data.network.model

sealed interface ProfileRequest {
    class GetPersonalData() : ProfileRequest
    class GetPayments() : ProfileRequest
    class UpdateProfileAvatar() : ProfileRequest
    class UpdatePersonalData() : ProfileRequest
    class UpdatePayments() : ProfileRequest
    class DeleteProfile() : ProfileRequest
}
