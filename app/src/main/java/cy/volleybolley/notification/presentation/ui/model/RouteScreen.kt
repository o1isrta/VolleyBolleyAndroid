package cy.volleybolley.notification.presentation.ui.model

enum class RouteScreen(val screenName: String) {
    JOIN_GAME("joinGame"),
    RATE("rate");

    companion object {
        fun fromScreenName(name: String?): RouteScreen? {
            return entries.find { it.screenName == name }
        }
    }
}
