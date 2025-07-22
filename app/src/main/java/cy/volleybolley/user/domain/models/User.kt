package cy.volleybolley.user.domain.models

data class User (
    val userId: Int,
    val firstName: String?,
    val lastName: String?,
    val genderType: String?,
    val paymentType: String,
    val paymentAccount: String?,
    val dateOfBirth: String?,
    val levelType: String?,
    val country: String?,
    val city: String?,
    val avatarUrl: String?,
    val isFavorite: Boolean?,
    val latestActivity: Array?,
)