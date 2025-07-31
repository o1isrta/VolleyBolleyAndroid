package cy.volleybolley.core

import cy.volleybolley.core.di.authorizationModule
import cy.volleybolley.core.di.coreModule

object DiProvider {
    val modules = listOf(
        coreModule,
        authorizationModule
    )
}
