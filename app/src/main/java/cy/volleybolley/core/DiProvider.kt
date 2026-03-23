package cy.volleybolley.core

import cy.volleybolley.auth.di.authorizationModule
import cy.volleybolley.auth.phone.di.authorizationByPhoneModule
import cy.volleybolley.core.di.coreModule
import cy.volleybolley.core.presentation.ui.screens.authorization.launch.di.launchModule
import cy.volleybolley.core.presentation.ui.screens.createNewGame.createNewGameModule
import cy.volleybolley.courts.di.courtsModule
import cy.volleybolley.games.di.gamesModule
import cy.volleybolley.jointhegame.di.joinTheGameModule
import cy.volleybolley.notification.di.notificationsModule
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
        playersModule,
        referenceDataModule,
        createNewGameModule,
        launchModule,
        authorizationModule,
        registrationModule,
        authorizationByPhoneModule,
        ratePlayersModule,
        notificationsModule,
        joinTheGameModule,
        profileModule
    )
}
