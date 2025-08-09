package cy.volleybolley.core

import cy.volleybolley.core.di.coreModule
import cy.volleybolley.courts.di.courtsModule
import cy.volleybolley.notification.di.fcmTokenModule

object DiProvider {
    val modules = listOf(
        coreModule,
        courtsModule,
        fcmTokenModule,
    )
}
