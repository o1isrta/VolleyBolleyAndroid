package cy.volleybolley.authorization.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("user_id") val userId: Int,
    @SerialName("is_registered") val isRegistered: Boolean,
    val avatar: String? = null,
    @SerialName("first_name") val firstName: String? = null,
    @SerialName("last_name") val lastName: String? = null,
    @SerialName("gender") val genderId: Int? = null,
    @SerialName("date_of_birth") val dateOfBirth: String? = null,
    @SerialName("level") val levelId: Int? = null,
    @SerialName("country_id") val countryId: Int? = null,
    @SerialName("city_id") val cityId: Int? = null
)
