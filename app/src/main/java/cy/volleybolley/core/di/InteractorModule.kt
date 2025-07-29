package cy.volleybolley.core.di

import cy.volleybolley.courts.domain.CourtsUseCaseImpl
import cy.volleybolley.courts.domain.api.CourtsUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val interactorModule = module {
    singleOf(::CourtsUseCaseImpl) bind CourtsUseCase::class
}
