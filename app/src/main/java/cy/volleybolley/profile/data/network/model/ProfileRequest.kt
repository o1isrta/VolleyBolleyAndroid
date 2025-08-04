package cy.volleybolley.profile.data.network.model

import cy.volleybolley.profile.data.dto.AvatarDto
import cy.volleybolley.profile.data.dto.PaymentDto
import cy.volleybolley.profile.data.dto.PersonalDataUpdateBody

sealed interface ProfileRequest {
    class GetPersonalData(
        val accessToken: String? = null,
        val path: String = "players/me",
    ) : ProfileRequest

    class GetPayments(
        val accessToken: String? = null,
        val path: String = "players/me/payments",
    ) : ProfileRequest

    class UpdatePersonalData(
        val accessToken: String? = null,
        val path: String = "players/me",
        val body: PersonalDataUpdateBody,
    ) : ProfileRequest

    class UpdatePayments(
        val accessToken: String? = null,
        val path: String = "players/me/payments",
        val body: List<PaymentDto>,
    ) : ProfileRequest

    class UpdateProfileAvatar(
        val accessToken: String? = null,
        val path: String = "players/me/avatar",
        val body: AvatarDto,
    ) : ProfileRequest

    class DeleteProfile(
        val accessToken: String? = null,
        val path: String = "players/me",
    ) : ProfileRequest
}
