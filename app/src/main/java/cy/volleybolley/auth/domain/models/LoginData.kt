package cy.volleybolley.auth.domain.models

import cy.volleybolley.profile.domain.model.PersonalData

class LoginData (
    val accessToken: String,
    val refreshToken: String,
    val userPersonalData: PersonalData,
)
