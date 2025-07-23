package cy.volleybolley.core.di

import cy.volleybolley.courts.domain.CourtsInteractorImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val interactorModule = module {
    singleOf(::CourtsInteractorImpl).bind()
}