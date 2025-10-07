package cy.volleybolley.core.presentation.ui.model

import cy.volleybolley.courts.domain.model.Location
import cy.volleybolley.profile.domain.model.Payment
import cy.volleybolley.profile.domain.model.PaymentType
import cy.volleybolley.profile.domain.model.PersonalData
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.model.PlayerActivityTemp
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.model.PlayerDetailTemp
import cy.volleybolley.profile.presentation.ui.screens.players.model.PlayerTemp
import cy.volleybolley.referencedata.domain.model.City
import cy.volleybolley.referencedata.domain.model.Country

object VolleyMocks {
    const val USER_NAME = "User"
    const val USER_SURNAME = "Userovich"
    const val USER_AVATAR = "https://cdn.fishki.net/upload/post/2021/03/29/3682461/gallery/tn/" +
        "wil-hughes-troll-face.jpg"
    const val USER_LEVEL = "PRO"

    const val MOCK_FAQ = "# Registration\n" +
        "To find the right games and teammates, choose your current skill level:\n" +
        "Options:\n" +
        "- Light (L1-L3) - Beginner\n" +
        "- Medium (M1-M3) - Confident amateur\n" +
        "- Hard (H1-H3) - Advanced\n" +
        "- Pro (P1-P3) - Professional\n" +
        "The higher the number, the higher the skill. You may change later based on player ratings.\n" +
        "# Post-Game Ratings\n" +
        "After each game, teammates can rate your level.\n" +
        "Once you receive 6 ratings, your level may change:\n" +
        "- 5+ positive ratings — promoted one step\n" +
        "- 5+ negative ratings — demoted one step\n" +
        "- Mixed feedback — level stays the same\n" +
        "# Want to move to the next category?\n" +
        "Earn 10 points from higher-level players within the last 60 days. The higher the evaluator's level," +
        " the more weight their rating carries.\n" +
        "# Levels can drop due to:\n" +
        "Ratings (6 within 60 days, 5+ downs = demotion)\n" +
        "Inactivity:\n" +
        "- 90 days = minus 1 step\n" +
        "- 180 days = reset to lowest level in current category\n" +
        "# Fair Play Policy\n" +
        "We ensure a fair system:\n" +
        "- Ratings are anonymous\n" +
        "- Max 2 ratings from the same player in 60 days\n" +
        "- Light has minimal impact on Hard and Pro levels\n" +
        "- Pro levels are harder to reach\n" +
        "Play fair — your level will speak for itself."

    val mockPersonalData = PersonalData(
        firstName = USER_NAME,
        lastName = USER_SURNAME,
        gender = "MALE",
        birthDate = "1987-03-23",
        level = "LIGHT",
        countryId = 0,
        cityId = 1,
        avatar = USER_AVATAR
    )

    val countries = listOf(Country(
        id = 0,
        name = "Thailand",
        cities = listOf(
            City(id = 0, name = "Koh Phangan"),
            City(id = 1, name = "Koh Samui")
        )
    ))

    val mockLocation = Location(
        longitude = 7.866269,
        latitude = 98.396756,
        courtName = "Phuket Municipal Stadium",
        locationName = "Mueang Phuket District"
    )

    val mockPlayers: List<PlayerTemp> = listOf(
        PlayerTemp(
            id = 1,
            firstName = "Иван",
            lastName = "Иванов",
            avatarUrl = null,
            isFavorite = true,
            level = USER_LEVEL
        ),
        PlayerTemp(
            id = 2,
            firstName = "Анна",
            lastName = "Петрова",
            avatarUrl = null,
            isFavorite = false,
            level = USER_LEVEL
        ),
        PlayerTemp(
            id = 3,
            firstName = "Сергей",
            lastName = "Смирнов",
            avatarUrl = null,
            isFavorite = true,
            level = USER_LEVEL
        ),
        PlayerTemp(
            id = 4,
            firstName = "Елена",
            lastName = "Васильева",
            avatarUrl = null,
            isFavorite = false,
            level = USER_LEVEL
        ),
    )

    val mockPlayerDetails: List<PlayerDetailTemp> = listOf(
        PlayerDetailTemp(
            id = 1,
            firstName = "Иван",
            lastName = "Иванов",
            avatarUrl = null,
            isFavorite = true,
            level = USER_LEVEL,
            latestActivity = listOf()
        ),
        PlayerDetailTemp(
            id = 2,
            firstName = "Анна",
            lastName = "Петрова",
            avatarUrl = null,
            isFavorite = false,
            level = USER_LEVEL,
            latestActivity = listOf(
                PlayerActivityTemp(
                    eventTimestamp = "2025-07-13T14:23:45Z",
                    courtLocation = Location(
                        longitude = 37.6173,
                        latitude = 55.7558,
                        courtName = "Спортивный комплекс",
                        locationName = "Красногорск"
                    )
                ),
            )
        ),
        PlayerDetailTemp(
            id = 3,
            firstName = "Сергей",
            lastName = "Смирнов",
            avatarUrl = null,
            isFavorite = true,
            level = USER_LEVEL,
            latestActivity = listOf(
                PlayerActivityTemp(
                    eventTimestamp = "2025-08-16T14:23:45Z",
                    courtLocation = Location(
                        longitude = 37.6156,
                        latitude = 55.7536,
                        courtName = "Зал №2",
                        locationName = "Химки"
                    )
                ),
                PlayerActivityTemp(
                    eventTimestamp = "2025-08-14T23:23:45Z",
                    courtLocation = Location(
                        longitude = 37.6194,
                        latitude = 55.7523,
                        courtName = "Арена Восток",
                        locationName = "Казань"
                    )
                ),
            )
        ),
        PlayerDetailTemp(
            id = 4,
            firstName = "Елена",
            lastName = "Васильева",
            avatarUrl = null,
            isFavorite = false,
            level = USER_LEVEL,
            latestActivity = listOf(
                PlayerActivityTemp(
                    eventTimestamp = "2025-08-16T14:23:45Z",
                    courtLocation = Location(
                        longitude = 37.6176,
                        latitude = 55.7538,
                        courtName = "Турнир Кубка",
                        locationName = "Новосибирск"
                    )
                ),
            )
        )
    )

    val mockPayments: List<Payment> = listOf(
        Payment(
            type = PaymentType.THAIBANK,
            account = "000 000 0000",
            isPreferred = false
        ),
        Payment(
            type = PaymentType.CASH,
            account = "",
            isPreferred = true
        ),
        Payment(
            type = PaymentType.REVOLUT,
            account = "",
            isPreferred = false
        )
    )
}
