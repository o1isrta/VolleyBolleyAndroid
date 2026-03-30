package cy.volleybolley.core.presentation.ui.model

import cy.volleybolley.core.domain.model.LevelType
import cy.volleybolley.courts.domain.model.Location
import cy.volleybolley.games.domain.model.entity.Host
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.entity.Team
import cy.volleybolley.games.domain.model.event.tournament.TournamentDetails
import cy.volleybolley.profile.domain.model.Payment
import cy.volleybolley.profile.domain.model.PaymentType
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.model.PlayerActivityTemp
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.model.PlayerDetailTemp
import cy.volleybolley.profile.presentation.ui.screens.players.model.PlayerTemp

object VolleyMocks {
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

    private const val HOST_NAME = "Artem Ivanov"
    private const val GENDER_MIX = "Mix"
    private val PAYMENT_TYPE_THAI_BANK = cy.volleybolley.core.domain.model.PaymentType.THAIBANK
    private const val CURRENCY_USD = "$"
    private const val PAYMENT_ACCOUNT = "988 016 7890"
    private val LEVELS_LIGHT = listOf("Light")
    private const val SHORT_MESSAGE: String = "Afterlunch meet. 6$ entry fee, our favorite place, don’t miss"

    private const val PLAYER_1 = "Anton Ivanov"
    private const val PLAYER_2 = "Aleksandr Abramov"
    private const val PLAYER_3 = "Anya Levan"
    private const val PLAYER_4 = "Alina Lyubimova"
    private const val PLAYER_5 = "Maxim Petrov"
    private const val PLAYER_6 = "Julia Petrova"
    private const val PLAYER_7 = "Tatiana Kalinina"
    private const val PLAYER_8 = "Artem Artemov"

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

    val mockTournament: TournamentDetails = TournamentDetails(
        tournamentId = 0,
        isIndividual = true,
        host = mockHost(),
        message = SHORT_MESSAGE,
        courtLocation = mockLocation,
        startTime = timeRangeFor().first,
        endTime = timeRangeFor().second,
        gender = GENDER_MIX,
        levels = LEVELS_LIGHT,
        maximumPlayers = 4,
        maximumTeams = 4,
        pricePerPerson = "2",
        paymentType = PAYMENT_TYPE_THAI_BANK,
        paymentAccount = PAYMENT_ACCOUNT,
        currencyType = CURRENCY_USD,
        teams = mockTeams()
    )

    private fun mockHost(): Host = Host(
        id = 10,
        name = HOST_NAME,
        avatar = null,
        level = LevelType.LIGHT,
    )

    private fun timeRangeFor(): Pair<String, String> = "2025-10-10T18:00:00" to "2025-10-10T20:00:00"

    private fun mockTeams(): List<Team> = listOf(
        Team(
            teamId = 0,
            players = listOf(
                mockPlayer(0, PLAYER_1),
                mockPlayer(1, PLAYER_2)
            )
        ),
        Team(
            teamId = 1,
            players = listOf(
                mockPlayer(0, PLAYER_3),
                mockPlayer(1, PLAYER_4)
            )
        ),
        Team(
            teamId = 2,
            players = listOf(
                mockPlayer(0, PLAYER_5),
                mockPlayer(1, PLAYER_6)
            )
        ),
        Team(
            teamId = 3,
            players = listOf(
                mockPlayer(0, PLAYER_7),
                mockPlayer(1, PLAYER_8)
            )
        )
    )

    private fun mockPlayer(id: Int, name: String): PlayerShort {
        return PlayerShort(
            playerId = id,
            name = name,
            level = LevelType.HARD,
            avatar = ""
        )
    }
}
