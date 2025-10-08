package cy.volleybolley.core

import cy.volleybolley.auth.di.authViewModelModule
import cy.volleybolley.core.di.coreModule
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.di.authorizationModule
import cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.di.authorizationByPhoneModule
import cy.volleybolley.core.presentation.ui.screens.authorization.launch.di.launchModule
import cy.volleybolley.core.presentation.ui.screens.authorization.registration.di.registrationModule
import cy.volleybolley.courts.di.courtsModule
import cy.volleybolley.games.di.gamesModule
import cy.volleybolley.players.di.playersModule
import cy.volleybolley.profile.di.profileModule
import cy.volleybolley.rateplayers.di.ratePlayersModule
import cy.volleybolley.referencedata.di.referenceDataModule

object DiProvider {
    val modules = listOf(
        coreModule,
        courtsModule,
        authViewModelModule,
        gamesModule,
        profileModule,
        playersModule,
        referenceDataModule,
        profileModule,
        launchModule,
        authorizationModule,
        registrationModule,
        authorizationByPhoneModule,
        ratePlayersModule,
    )
}
