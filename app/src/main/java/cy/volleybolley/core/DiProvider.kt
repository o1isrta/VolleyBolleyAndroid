package cy.volleybolley.core

import cy.volleybolley.core.di.coreModule
import cy.volleybolley.courts.di.courtsModule
import cy.volleybolley.games.di.gamesModule

object DiProvider {
    val modules = listOf(
        coreModule,
        courtsModule,
        gamesModule,
    )
}
