package cy.volleybolley.core.di

import cy.volleybolley.courts.data.CourtsRepositoryImpl
import cy.volleybolley.courts.domain.api.CourtsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CourtsRepository>(CourtsClientQualifier) { CourtsRepositoryImpl(get()) }
}
