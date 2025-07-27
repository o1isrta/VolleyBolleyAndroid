package cy.volleybolley.core

import cy.volleybolley.auth.di.authViewModelModule
import cy.volleybolley.core.di.coreModule

object DiProvider {
    val modules = listOf(
        coreModule,
        authViewModelModule
    )
}
