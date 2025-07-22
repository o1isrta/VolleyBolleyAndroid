package cy.volleybolley.user.data.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class UserDto (
    @SerialName("first_name") val firstName: String?,
    @SerialName("last_name") val lastName: String?,
    @SerialName("gender_type") val genderType: String?,
    @SerialName("payment_type") val paymentType: String,
    @SerialName("payment_account") val paymentAccount: String?,
    @SerialName("date_of_birth") val dateOfBirth: String?,
    @SerialName("level_type") val levelType: String?,
    @SerialName("country") val country: String?,
    @SerialName("city") val city: String?,
    @SerialName("avatar_url") val avatarUrl: String?,
    @SerialName("is_favorite") val isFavorite: Boolean?,
    @SerialName("latest_activity") val latestActivity: Array?,
)
