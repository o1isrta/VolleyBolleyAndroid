package cy.volleybolley.notification.presentation

import cy.volleybolley.core.presentation.ui.navigation.JoinTheGameRoute
import cy.volleybolley.core.presentation.ui.navigation.LaunchRoute
import cy.volleybolley.core.presentation.ui.navigation.NavMap
import cy.volleybolley.core.presentation.ui.navigation.RatePlayersRoute

fun resolveNotificationRoute(screen: String?, gameId: String?): NavMap? {
    return when (RouteScreen.fromScreenName(screen)) {
        RouteScreen.JOIN_GAME -> gameId?.let { JoinTheGameRoute(it) }
        RouteScreen.RATE -> gameId?.let { RatePlayersRoute(it) }
        else -> LaunchRoute
    }
}
