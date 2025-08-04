package cy.volleybolley.profile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PersonalDataUpdateBody(
    @SerialName("first_name") var firstName: String? = null,
    @SerialName("last_name") var lastName: String? = null,
    @SerialName("gender") var gender: String? = null,
    @SerialName("date_of_birth") var birthDate: String? = null,
    @SerialName("level") var level: String? = null,
    @SerialName("country_id") var countryId: String? = null,
    @SerialName("city_id") var cityId: String? = null,
)
