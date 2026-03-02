package cy.volleybolley.notification.presentation.ui.model

enum class RouteScreen(val screenName: String) {
    INVITE_GAME("invite_game"),
    INVITE_TOURNEY("invite_tourney"),
    REMOVED_GAME("removed_game"),
    REMOVED_TOURNEY("removed_tourney"),
    CANCELED_GAME("canceled_game"),
    CANCELED_TOURNEY("canceled_tourney"),
    RATE_GAME("rate_game"),
    RATE_TOURNEY("rate_tourney");

    companion object {
        fun fromScreenName(name: String?): RouteScreen? {
            return entries.find { it.screenName == name }
        }
    }
}
