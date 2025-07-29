package cy.volleybolley.core.di

import cy.volleybolley.courts.data.CourtsRepositoryImpl
import cy.volleybolley.courts.domain.api.CourtsRepository
import cy.volleybolley.games.data.GamesRepositoryImpl
import cy.volleybolley.games.domain.api.GamesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CourtsRepository>(CourtsClientQualifier) { CourtsRepositoryImpl(get()) }
    single<GamesRepository>(CourtsClientQualifier) { GamesRepositoryImpl(get()) }
}
