package cy.volleybolley.profile.data.network.model

import cy.volleybolley.profile.data.dto.AvatarDto
import cy.volleybolley.profile.data.dto.PaymentDto
import cy.volleybolley.profile.data.dto.PersonalDataDto

sealed interface ProfileResponse {
    class GetPersonalData(
        val personalData: PersonalDataDto,
    ) : ProfileResponse

    class GetPayments(
        val payments: List<PaymentDto>,
    ) : ProfileResponse

    object UpdatePersonalData : ProfileResponse

    object UpdatePayments : ProfileResponse

    class UpdateProfileAvatar(
        val avatar: AvatarDto,
    ) : ProfileResponse

    object DeleteProfile : ProfileResponse
}
