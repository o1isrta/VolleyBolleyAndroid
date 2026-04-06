package cy.volleybolley.core.presentation.ui.screens.createNewGame.model

import cy.volleybolley.core.presentation.ui.model.Level
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
import cy.volleybolley.games.domain.model.event.game.CreateGame
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// GameData (UI) -> CreateGame (API)
fun GameData.toCreateGame(): CreateGame {
    return CreateGame(
        courtId = this.placeCourt.courtId,
        message = this.message,
        startTime = formatTimeWithDateForApi(this.date, this.startTime),
        endTime = formatTimeWithDateForApi(this.date, this.finishTime),
        gender = this.gender.toApiString(),
        levels = this.levels.map { it.toApiString() },
        isPrivate = this.isPrivate,
        maximumPlayers = this.maximumPlayers,
        pricePerPerson = this.perPerson,
        paymentType = this.paymentType,
        players = this.players.map { it. id }
    )
}

// форматирование времени в формат для API
private fun formatTimeWithDateForApi(date: LocalDate, time: VolleyTimeStamp?): String{
    if (time == null) return ""
    val hour = if (time.isAfternoon) time.hour + 12 else time.hour
    val dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
    val timeStr = "${
        hour.toString().padStart(2,'0')
    }:${
        time.minutes.toString().padStart(2,'0')
    }"
    return "$dateStr $timeStr"
}
