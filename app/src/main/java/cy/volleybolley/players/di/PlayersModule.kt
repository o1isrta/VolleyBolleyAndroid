package cy.volleybolley.players.di

import cy.volleybolley.players.data.network.PlayersNetworkClient
import cy.volleybolley.players.data.repository.PlayersRepositoryImpl
import cy.volleybolley.players.domain.repository.PlayersRepository
import org.koin.dsl.module

val playersModule = module {
    single { PlayersNetworkClient() }
    single<PlayersRepository> { PlayersRepositoryImpl(get()) }
}
