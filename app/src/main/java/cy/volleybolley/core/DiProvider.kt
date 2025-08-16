package cy.volleybolley.core

import cy.volleybolley.core.di.coreModule
import cy.volleybolley.courts.di.courtsModule
import cy.volleybolley.players.di.playersModule

object DiProvider {
    val modules = listOf(
        coreModule,
        courtsModule,
        playersModule
    )
}
