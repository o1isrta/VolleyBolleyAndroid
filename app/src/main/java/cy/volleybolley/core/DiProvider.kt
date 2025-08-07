package cy.volleybolley.core

import cy.volleybolley.auth.di.authViewModelModule
import cy.volleybolley.core.di.coreModule
import cy.volleybolley.courts.di.courtsModule

object DiProvider {
    val modules = listOf(
        coreModule,
        courtsModule,
        authViewModelModule
    )
}
