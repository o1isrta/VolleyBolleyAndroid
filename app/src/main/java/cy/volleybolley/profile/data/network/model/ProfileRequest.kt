package cy.volleybolley.profile.data.network.model

import cy.volleybolley.profile.data.dto.AvatarDto
import cy.volleybolley.profile.data.dto.PaymentDto
import cy.volleybolley.profile.data.dto.PersonalDataUpdateBody

sealed interface ProfileRequest {
    class GetPersonalData(
        val accessToken: String? = null,
        val path: String = PLAYERS_ME,
    ) : ProfileRequest

    class GetPayments(
        val accessToken: String? = null,
        val path: String = PLAYERS_ME_PAYMENTS,
    ) : ProfileRequest

    class UpdatePersonalData(
        val accessToken: String? = null,
        val path: String = PLAYERS_ME,
        val body: PersonalDataUpdateBody,
    ) : ProfileRequest

    class UpdatePayments(
        val accessToken: String? = null,
        val path: String = PLAYERS_ME_PAYMENTS,
        val body: List<PaymentDto>,
    ) : ProfileRequest

    class UpdateProfileAvatar(
        val accessToken: String? = null,
        val path: String = PLAYERS_ME_AVATAR,
        val body: AvatarDto,
    ) : ProfileRequest

    class DeleteProfile(
        val accessToken: String? = null,
        val path: String = PLAYERS_ME,
    ) : ProfileRequest
}

private const val PLAYERS_ME = "players/me"
private const val PLAYERS_ME_PAYMENTS = "players/me/payments"
private const val PLAYERS_ME_AVATAR = "players/me/avatar"
