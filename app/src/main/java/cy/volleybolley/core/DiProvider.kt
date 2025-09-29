package cy.volleybolley.core

import cy.volleybolley.auth.di.authViewModelModule
import cy.volleybolley.core.di.coreModule
import cy.volleybolley.courts.di.courtsModule
import cy.volleybolley.games.di.gamesModule
import cy.volleybolley.profile.di.profileModule
import cy.volleybolley.players.di.playersModule
import cy.volleybolley.profile.di.profileModule
import cy.volleybolley.referencedata.di.referenceDataModule

object DiProvider {
    val modules = listOf(
        coreModule,
        courtsModule,
        gamesModule,
        profileModule,
        playersModule,
        authViewModelModule,
        referenceDataModule,
    )
}
