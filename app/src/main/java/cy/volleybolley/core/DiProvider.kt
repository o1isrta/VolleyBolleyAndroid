package cy.volleybolley.core

import cy.volleybolley.core.di.coreModule
import cy.volleybolley.core.di.dataModule
import cy.volleybolley.core.di.interactorModule
import cy.volleybolley.core.di.repositoryModule

object DiProvider {
    val modules = listOf(
        coreModule,
        dataModule,
        repositoryModule,
        interactorModule,
    )
}