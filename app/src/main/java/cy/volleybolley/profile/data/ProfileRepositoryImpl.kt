package cy.volleybolley.profile.data

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.profile.data.dto.AvatarDto
import cy.volleybolley.profile.data.dto.toDomain
import cy.volleybolley.profile.data.dto.toDto
import cy.volleybolley.profile.data.dto.toUpdateBody
import cy.volleybolley.profile.data.network.model.ProfileRequest
import cy.volleybolley.profile.data.network.model.ProfileResponse
import cy.volleybolley.profile.domain.api.ProfileRepository
import cy.volleybolley.profile.domain.model.Payment
import cy.volleybolley.profile.domain.model.PersonalData

class ProfileRepositoryImpl(
    val networkClient: NetworkClient<ProfileRequest, ProfileResponse>
) : ProfileRepository {
    override suspend fun getPersonalData(accessToken: String?): VolleyResult<PersonalData, ErrorType> {
        val response = networkClient.getResponse(ProfileRequest.GetPersonalData(accessToken))
        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
        val personalData = (response.body as? ProfileResponse.GetPersonalData)?.personalData?.toDomain()
        return personalData?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun getPayments(accessToken: String?): VolleyResult<List<Payment>, ErrorType> {
        val response = networkClient.getResponse(ProfileRequest.GetPayments(accessToken))
        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
        val payments = (response.body as? ProfileResponse.GetPayments)?.payments?.toDomain()
        return payments?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun updatePersonalData(
        accessToken: String?,
        data: PersonalData
    ): VolleyResult<Unit, ErrorType> {
        val response = networkClient.getResponse(
            ProfileRequest.UpdatePersonalData(
                accessToken = accessToken,
                body = data.toUpdateBody()
            )
        )
        return if (response.isSuccess) VolleyResult.Success(Unit) else VolleyResult.Failure(response.resultCode.mapToErrorType())
    }

    override suspend fun updatePayments(
        accessToken: String?,
        payments: List<Payment>
    ): VolleyResult<Unit, ErrorType> {
        val response = networkClient.getResponse(
            ProfileRequest.UpdatePayments(
                accessToken = accessToken,
                body = payments.toDto()
            )
        )
        return if (response.isSuccess) VolleyResult.Success(Unit) else VolleyResult.Failure(response.resultCode.mapToErrorType())
    }

    override suspend fun updateAvatar(
        accessToken: String?,
        avatarBase64String: String
    ): VolleyResult<String, ErrorType> {
        val response = networkClient.getResponse(
            ProfileRequest.UpdateProfileAvatar(
                accessToken = accessToken,
                body = AvatarDto(avatarBase64String)
            )
        )
        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
        val avatar = (response.body as? ProfileResponse.UpdateProfileAvatar)?.avatar?.toString()
        return avatar?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun deleteProfile(accessToken: String?): VolleyResult<Unit, ErrorType> {
        val response = networkClient.getResponse(ProfileRequest.DeleteProfile(accessToken))
        return if (response.isSuccess) VolleyResult.Success(Unit) else VolleyResult.Failure(response.resultCode.mapToErrorType())
    }

}
