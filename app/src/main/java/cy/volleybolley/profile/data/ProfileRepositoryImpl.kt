package cy.volleybolley.profile.data

import android.util.Base64
import cy.volleybolley.auth.domain.api.storage.UserStorage
import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.profile.data.dto.AvatarDto
import cy.volleybolley.profile.data.dto.getActualUpdateBody
import cy.volleybolley.profile.data.dto.toDomain
import cy.volleybolley.profile.data.dto.toUpdateBody
import cy.volleybolley.profile.data.network.model.ProfileRequest
import cy.volleybolley.profile.data.network.model.ProfileResponse
import cy.volleybolley.profile.domain.api.ProfileRepository
import cy.volleybolley.profile.domain.model.Payment
import cy.volleybolley.profile.domain.model.PersonalData
import kotlinx.serialization.json.Json

class ProfileRepositoryImpl(
    private val networkClient: NetworkClient<ProfileRequest, ProfileResponse>,
    private val userStorage: UserStorage,
    private val json: Json,
) : ProfileRepository {
    private var lastReceivedPersonalData: PersonalData? = null

    override suspend fun getPersonalData(): VolleyResult<PersonalData, ErrorType> {
        val response = networkClient.getResponse(ProfileRequest.GetPersonalData())
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
        val response = networkClient.getResponse(ProfileRequest.GetPayments())
        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
        val payments = (response.body as? ProfileResponse.GetPayments)?.payments?.toDomain()
        return payments?.let { VolleyResult.Success(it) } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }

    override suspend fun updatePersonalData(
        newPersonalData: PersonalData,
        cachedPersonalData: PersonalData?,
    ): VolleyResult<Unit, ErrorType> {
        cachedPersonalData?.let { lastReceivedPersonalData = it }
        val actualChangesOnPersonalData = newPersonalData.getActualUpdateBody(lastReceivedPersonalData)
        val response = networkClient.getResponse(
            ProfileRequest.UpdatePersonalData(
                body = json.encodeToString(actualChangesOnPersonalData)
            )
        )
        return if (response.isSuccess) {
            userStorage.savePersonalData(newPersonalData)
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
        photoBytes: ByteArray?,
    ): VolleyResult<String, ErrorType> {
        val response = networkClient.getResponse(
            ProfileRequest.UpdateProfileAvatar(
                body = AvatarDto(convertImageBytesToBase64String(photoBytes))
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
        val response = networkClient.getResponse(ProfileRequest.DeleteProfile())
        return if (response.isSuccess) {
            VolleyResult.Success(Unit)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }
    }

    private fun handleAvatarNullValue(avatar: String?): String = avatar ?: ""

    private fun convertImageBytesToBase64String(imageBytes: ByteArray?): String? {
        return imageBytes?.let { Base64.encodeToString(it, Base64.DEFAULT) }
    }
}
