package cy.volleybolley.core

import cy.volleybolley.auth.di.authViewModelModule
import cy.volleybolley.core.di.coreModule
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.di.AuthorizationModule
import cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.di.authorizationByPhoneModule
import cy.volleybolley.core.presentation.ui.screens.authorization.launch.di.launchModule
import cy.volleybolley.core.presentation.ui.screens.authorization.registration.di.registrationModule
import cy.volleybolley.courts.di.courtsModule
import cy.volleybolley.players.di.playersModule
import cy.volleybolley.profile.di.profileModule
import cy.volleybolley.referencedata.di.referenceDataModule

object DiProvider {
    val modules = listOf(
        coreModule,
        courtsModule,
        authViewModelModule,
        playersModule,
        referenceDataModule,
        profileModule,
        launchModule,
        AuthorizationModule,
        registrationModule,
        authorizationByPhoneModule
    )
}
