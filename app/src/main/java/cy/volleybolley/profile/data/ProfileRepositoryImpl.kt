package cy.volleybolley.profile.data

import android.util.Base64
import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.profile.data.dto.AvatarDto
import cy.volleybolley.profile.data.dto.toDomain
import cy.volleybolley.profile.data.dto.toUpdateBody
import cy.volleybolley.profile.data.network.model.ProfileRequest
import cy.volleybolley.profile.data.network.model.ProfileResponse
import cy.volleybolley.profile.domain.api.ProfileRepository
import cy.volleybolley.profile.domain.model.Payment
import cy.volleybolley.profile.domain.model.PersonalData

class ProfileRepositoryImpl(
    private val networkClient: NetworkClient<ProfileRequest, ProfileResponse>,
    private var accessToken: String? = null,
) : ProfileRepository {
    private var lastReceivedPersonalData: PersonalData? = null

    override suspend fun getPersonalData(): VolleyResult<PersonalData, ErrorType> {
        val response = networkClient.getResponse(ProfileRequest.GetPersonalData(accessToken))
        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
        val personalData = (response.body as? ProfileResponse.GetPersonalData)?.personalData?.toDomain()
        return personalData?.let {
            lastReceivedPersonalData = it
            VolleyResult.Success(it)
        } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun getPayments(): VolleyResult<List<Payment>, ErrorType> {
        val response = networkClient.getResponse(ProfileRequest.GetPayments(accessToken))
        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
        val payments = (response.body as? ProfileResponse.GetPayments)?.payments?.toDomain()
        return payments?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun updatePersonalData(
        personalData: PersonalData,
    ): VolleyResult<Unit, ErrorType> {
        val actualChangesOnPersonalData = lastReceivedPersonalData?.getChangedPersonalDataFields(personalData)
        val response = networkClient.getResponse(
            ProfileRequest.UpdatePersonalData(
                accessToken = accessToken,
                body = actualChangesOnPersonalData?.toUpdateBody() ?: personalData.toUpdateBody()
            )
        )
        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }

    override suspend fun updatePayments(
        payments: List<Payment>,
    ): VolleyResult<Unit, ErrorType> {
        val response = networkClient.getResponse(
            ProfileRequest.UpdatePayments(
                accessToken = accessToken,
                body = payments.toUpdateBody()
            )
        )
        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }

    override suspend fun updateAvatar(
        imageBytes: ByteArray?,
    ): VolleyResult<String, ErrorType> {
        val response = networkClient.getResponse(
            ProfileRequest.UpdateProfileAvatar(
                accessToken = accessToken,
                body = AvatarDto(convertImageBytesToBase64String(imageBytes))
            )
        )
        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
        val avatar = (response.body as? ProfileResponse.UpdateProfileAvatar)?.avatar
        return avatar?.let {
            VolleyResult.Success(handleAvatarNullValue(it.avatar))
        } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun deleteProfile(): VolleyResult<Unit, ErrorType> {
        val response = networkClient.getResponse(ProfileRequest.DeleteProfile(accessToken))
        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }

    private fun handleAvatarNullValue(avatar: String?): String = avatar ?: ""

    fun updateAccessToken(newAccessToken: String) {
        accessToken = newAccessToken
    }

    private fun convertImageBytesToBase64String(imageBytes: ByteArray?): String? {
        return imageBytes?.let { Base64.encodeToString(it, Base64.DEFAULT) }
    }

    private fun PersonalData.getChangedPersonalDataFields(newData: PersonalData): PersonalData {
        return PersonalData(
            firstName = firstName.checkSameStringField(newData.firstName),
            lastName = lastName.checkSameStringField(newData.lastName),
            gender = gender.checkSameStringField(newData.gender),
            birthDate = birthDate.checkSameStringField(newData.birthDate),
            level = level.checkSameStringField(newData.level),
            countryId = countryId.checkSameIntField(newData.countryId),
            cityId = cityId.checkSameIntField(newData.cityId),
            avatar = avatar
        )
    }

    private fun String.checkSameStringField(newString: String): String = if (this == newString) "" else newString
    private fun Int.checkSameIntField(newInt: Int): Int = if (this.toInt() == newInt) -1 else newInt
}
