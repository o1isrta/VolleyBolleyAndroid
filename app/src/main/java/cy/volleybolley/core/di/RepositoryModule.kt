package cy.volleybolley.core.di

import cy.volleybolley.courts.data.CourtsRepositoryImpl
import cy.volleybolley.courts.domain.api.CourtsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::CourtsRepositoryImpl) bind CourtsRepository::class
}
