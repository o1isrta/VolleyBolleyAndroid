package cy.volleybolley.games.domain.model.event

enum class EventFilter(val title: String) {
    MY_GAMES("My games"),
    UPCOMING("Upcoming games"),
    ARCHIVE("Archive"),
    INVITES("Game invites")
}
