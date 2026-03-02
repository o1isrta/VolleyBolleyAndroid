package cy.volleybolley.notification.presentation.ui.component

import cy.volleybolley.core.presentation.ui.navigation.JoinTheGameRoute
import cy.volleybolley.core.presentation.ui.navigation.JoinTheTourneyRoute
import cy.volleybolley.core.presentation.ui.navigation.LaunchRoute
import cy.volleybolley.core.presentation.ui.navigation.NavMap
import cy.volleybolley.core.presentation.ui.navigation.RatePlayersRoute
import cy.volleybolley.games.domain.model.event.EventType
import cy.volleybolley.notification.presentation.ui.model.RouteScreen

fun resolveNotificationRoute(screen: String?, eventId: Int?): NavMap? {
    return when (RouteScreen.Companion.fromScreenName(screen)) {
        RouteScreen.INVITE_GAME -> eventId?.let { JoinTheGameRoute(it) }
        RouteScreen.INVITE_TOURNEY -> eventId?.let { JoinTheTourneyRoute(it) }
        RouteScreen.REMOVED_GAME -> LaunchRoute
        RouteScreen.REMOVED_TOURNEY -> LaunchRoute
        RouteScreen.CANCELED_GAME -> LaunchRoute
        RouteScreen.CANCELED_TOURNEY -> LaunchRoute
        RouteScreen.RATE_GAME -> eventId?.let {
            RatePlayersRoute(
                eventId = it,
                eventType = EventType.GAME
            )
        }

        RouteScreen.RATE_TOURNEY -> eventId?.let {
            RatePlayersRoute(
                eventId = it,
                eventType = EventType.TOURNAMENT
            )
        }

        else -> LaunchRoute
    }
}
