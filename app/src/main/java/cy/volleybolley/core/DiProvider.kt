package cy.volleybolley.core

import cy.volleybolley.auth.di.authorizationModule
import cy.volleybolley.core.di.coreModule
import cy.volleybolley.phone.di.authorizationByPhoneModule
import cy.volleybolley.core.presentation.ui.screens.authorization.launch.di.launchModule
import cy.volleybolley.courts.di.courtsModule
import cy.volleybolley.games.di.gamesModule
import cy.volleybolley.players.di.playersModule
import cy.volleybolley.profile.di.profileModule
import cy.volleybolley.rateplayers.di.ratePlayersModule
import cy.volleybolley.referencedata.di.referenceDataModule
import cy.volleybolley.registration.di.registrationModule

object DiProvider {
    val modules = listOf(
        coreModule,
        courtsModule,
        gamesModule,
        profileModule,
        playersModule,
        referenceDataModule,
        launchModule,
        authorizationModule,
        registrationModule,
        authorizationByPhoneModule,
        ratePlayersModule,
    )
}
