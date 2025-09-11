package cy.volleybolley.notification.presentation.ui.component

import cy.volleybolley.core.presentation.ui.navigation.JoinTheGameRoute
import cy.volleybolley.core.presentation.ui.navigation.LaunchRoute
import cy.volleybolley.core.presentation.ui.navigation.NavMap
import cy.volleybolley.core.presentation.ui.navigation.RatePlayersRoute
import cy.volleybolley.notification.presentation.ui.model.RouteScreen

fun resolveNotificationRoute(screen: String?, gameId: String?): NavMap? {
    return when (RouteScreen.Companion.fromScreenName(screen)) {
        RouteScreen.JOIN_GAME -> gameId?.let { JoinTheGameRoute(it) }
        RouteScreen.RATE -> gameId?.let { RatePlayersRoute(it) }
        else -> LaunchRoute
    }
}
