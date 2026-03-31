package cy.volleybolley.profile.data.network.model

import cy.volleybolley.profile.data.dto.AvatarDto
import cy.volleybolley.profile.data.dto.PaymentsUpdateBodyDto

sealed interface ProfileRequest {
    class GetPersonalData(
        val path: String = PLAYERS_ME,
    ) : ProfileRequest

    class GetPayments(
        val path: String = "${PLAYERS_ME}payments/",
    ) : ProfileRequest

    class UpdatePersonalData(
        val path: String = PLAYERS_ME,
        val body: String,
    ) : ProfileRequest

    class UpdatePayments(
        val path: String = "${PLAYERS_ME}payments/",
        val body: PaymentsUpdateBodyDto,
    ) : ProfileRequest

    class UpdateProfileAvatar(
        val path: String = "${PLAYERS_ME}avatar/",
        val body: AvatarDto,
    ) : ProfileRequest

    class DeleteProfile(
        val path: String = PLAYERS_ME,
    ) : ProfileRequest
}

private const val PLAYERS_ME = "/players/me/"
