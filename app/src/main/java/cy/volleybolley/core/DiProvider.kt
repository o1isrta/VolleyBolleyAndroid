package cy.volleybolley.core

import cy.volleybolley.auth.di.authViewModelModule
import cy.volleybolley.core.di.coreModule
import cy.volleybolley.courts.di.courtsModule
import cy.volleybolley.profile.di.profileModule

object DiProvider {
    val modules = listOf(
        coreModule,
        courtsModule,
        authViewModelModule,
        profileModule
    )
}
