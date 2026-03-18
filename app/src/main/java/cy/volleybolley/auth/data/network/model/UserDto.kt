package cy.volleybolley.auth.data.network.model

import cy.volleybolley.profile.domain.model.PersonalData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UserDto(
    @SerialName("player_id") val playerId: Int,
    @SerialName("is_registered") val isRegistered: Boolean,
    @SerialName("avatar") val avatar: String?,
    @SerialName("first_name") val firstName: String?,
    @SerialName("last_name") val lastName: String?,
    @SerialName("gender") val gender: String?,
    @SerialName("date_of_birth") val dateOfBirth: String?,
    @SerialName("level") val level: String?,
    @SerialName("country") val countryId: Int?,
    @SerialName("city") val cityId: Int?,
)

fun UserDto.toPersonalData(): PersonalData {
    return PersonalData(
        firstName = firstName.orEmpty(),
        lastName = lastName.orEmpty(),
        gender = gender.orEmpty(),
        birthDate = dateOfBirth.orEmpty(),
        level = level.orEmpty(),
        avatar = avatar,
        countryId = countryId ?: -1,
        cityId = cityId ?: -1,
    )
}
