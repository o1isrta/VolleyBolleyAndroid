package cy.volleybolley.core.presentation.ui.screens.games.archive.util

import cy.volleybolley.core.domain.model.LevelType
import cy.volleybolley.core.domain.model.PaymentType
import cy.volleybolley.courts.domain.model.Location
import cy.volleybolley.games.domain.model.entity.Host
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.entity.Team
import cy.volleybolley.games.domain.model.event.Event
import cy.volleybolley.games.domain.model.event.EventType
import cy.volleybolley.games.domain.model.event.game.GameDetails
import cy.volleybolley.games.domain.model.event.tournament.TournamentDetails

private const val HOST_NAME = "Artem Ivanov"
private const val GENDER_MIX = "Mix"
private val PAYMENT_TYPE_THAI_BANK = PaymentType.THAIBANK
private const val CURRENCY_USD = "$"
private const val PAYMENT_ACCOUNT = "988 016 7890"

private const val LATITUDE = 7.8471
private const val LONGITUDE = 98.2929

private val LEVELS_LIGHT = listOf("Light")

private val LONG_MESSAGE: String = """
    Hi! This is a really long test message to check how the bubble expands when there are many
    characters inside. It should properly wrap across multiple lines, no cuts.
""".trimIndent()

private const val SHORT_MESSAGE: String = "Afterlunch meet. 6$ entry fee, our favorite place, don’t miss"

private const val PLAYER_1 = "Anton Ivanov"
private const val PLAYER_2 = "Aleksandr Abramov"
private const val PLAYER_3 = "Anya Levan"
private const val PLAYER_4 = "Alina Lyubimova"
private const val PLAYER_5 = "Maxim Petrov"
private const val PLAYER_6 = "Julia Petrova"
private const val PLAYER_7 = "Tatiana Kalinina"
private const val PLAYER_8 = "Artem Artemov"

fun provideMockTeams(): List<Team> {
    return mockTeams()
}

fun provideMockGame(id: Int): GameDetails {
    return GameDetails(
        gameId = id,
        host = mockHost(),
        message = messageFor(id),
        isPrivate = false,
        courtLocation = locationFor(id),
        startTime = timeRangeFor(id).first,
        endTime = timeRangeFor(id).second,
        gender = GENDER_MIX,
        levels = LEVELS_LIGHT,
        pricePerPerson = "2",
        maximumPlayers = 4,
        paymentType = PAYMENT_TYPE_THAI_BANK,
        paymentAccount = PAYMENT_ACCOUNT,
        currencyType = CURRENCY_USD,
        players = mockPlayers()
    )
}

fun provideMockTourney(id: Int): TournamentDetails {
    return TournamentDetails(
        tournamentId = id,
        isIndividual = false,
        host = mockHost(),
        message = messageFor(id),
        courtLocation = locationFor(id),
        startTime = timeRangeFor(id).first,
        endTime = timeRangeFor(id).second,
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
}

fun provideMockItem(id: Int, type: EventType): Event {
    return Event(
        id = id,
        type = type,
        host = mockHost(),
        location = locationFor(id),
        message = messageFor(id),
        startTime = timeRangeFor(id).first,
        endTime = timeRangeFor(id).second
    )
}

private fun mockHost(): Host = Host(
    id = 10,
    name = HOST_NAME,
    avatar = null,
    level = LevelType.LIGHT,
)

private fun messageFor(id: Int): String = if (id == 1) LONG_MESSAGE else SHORT_MESSAGE

private fun locationFor(id: Int): Location = if (id == 1) {
    Location(
        longitude = LONGITUDE,
        latitude = LATITUDE,
        courtName = "Karon Beach Club",
        locationName = "Karon",
    )
} else {
    Location(
        longitude = LONGITUDE,
        latitude = LATITUDE,
        courtName = "The Shore at Katathani Resort",
        locationName = "Kata Noi",
    )
}

private fun timeRangeFor(id: Int): Pair<String, String> = if (id == 1) {
    "2025-10-10T18:00:00" to "2025-10-10T20:00:00"
} else {
    "2025-10-16T13:00:00" to "2025-10-16T14:00:00"
}

private fun mockPlayer(id: Int, name: String): PlayerShort {
    return PlayerShort(
        playerId = id,
        name = name,
        level = LevelType.HARD,
        avatar = ""
    )
}

private fun mockPlayers(): List<PlayerShort> = listOf(
    PlayerShort(
        name = HOST_NAME,
        level = LevelType.LIGHT,
        playerId = 0,
        avatar = "",
    ),
    PlayerShort(
        name = "Aleksandr Abramov",
        level = LevelType.LIGHT,
        playerId = 1,
        avatar = "",
    ),
)

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
