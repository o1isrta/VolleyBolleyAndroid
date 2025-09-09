package cy.volleybolley.core

import cy.volleybolley.auth.di.authViewModelModule
import cy.volleybolley.core.di.coreModule
import cy.volleybolley.courts.di.courtsModule
import cy.volleybolley.notification.di.notificationsModule

object DiProvider {
    val modules = listOf(
        coreModule,
        courtsModule,
        notificationsModule,
        authViewModelModule
    )
}
