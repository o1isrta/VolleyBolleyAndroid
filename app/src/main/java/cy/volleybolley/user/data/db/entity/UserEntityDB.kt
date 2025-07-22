package cy.volleybolley.user.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntityDB(
    @PrimaryKey
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