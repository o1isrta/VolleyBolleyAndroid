package cy.volleybolley.core

import cy.volleybolley.auth.di.authViewModelModule
import cy.volleybolley.core.di.coreModule
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.di.authorizationModule
import cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.di.authorizationByPhoneModule
import cy.volleybolley.core.presentation.ui.screens.authorization.launch.di.launchModule
import cy.volleybolley.core.presentation.ui.screens.authorization.registration.di.registrationModule
import cy.volleybolley.courts.di.courtsModule
import cy.volleybolley.notification.di.notificationsModule
import cy.volleybolley.players.di.playersModule
import cy.volleybolley.profile.di.profileModule
import cy.volleybolley.referencedata.di.referenceDataModule

object DiProvider {
    val modules = listOf(
        coreModule,
        courtsModule,
        playersModule,
        referenceDataModule,
        profileModule,
        launchModule,
        authorizationModule,
        registrationModule,
        authorizationByPhoneModule,
        notificationsModule,
        authViewModelModule
    )
}
