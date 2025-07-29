package cy.volleybolley.core.di

import cy.volleybolley.courts.domain.CourtsInteractorImpl
import cy.volleybolley.courts.domain.api.CourtsInteractor
import cy.volleybolley.games.data.GamesRepositoryImpl
import cy.volleybolley.games.domain.api.GamesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val interactorModule = module {
    singleOf(::CourtsInteractorImpl) bind CourtsInteractor::class
    singleOf(::GamesRepositoryImpl) bind GamesRepository::class
}
