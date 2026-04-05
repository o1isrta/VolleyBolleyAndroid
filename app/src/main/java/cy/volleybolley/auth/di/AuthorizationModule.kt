package cy.volleybolley.auth.di

import android.content.Context
import cy.volleybolley.auth.chooseMethod.AuthorizationViewModel
import cy.volleybolley.auth.data.AuthRepositoryImpl
import cy.volleybolley.auth.data.LoginDataRepositoryImpl
import cy.volleybolley.auth.data.RefreshTokenTimestampStorageImpl
import cy.volleybolley.auth.data.network.AuthNetworkClient
import cy.volleybolley.auth.data.network.model.AuthRequest
import cy.volleybolley.auth.data.network.model.AuthResponse
import cy.volleybolley.auth.data.storage.TokenStorageImpl
import cy.volleybolley.auth.data.storage.UserStorageImpl
import cy.volleybolley.auth.domain.api.AuthRepository
import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.auth.domain.api.RefreshTokenTimestampStorage
import cy.volleybolley.auth.domain.api.storage.TokenStorage
import cy.volleybolley.auth.domain.api.storage.UserStorage
import cy.volleybolley.auth.domain.api.usecase.CheckRefreshTokenExpirationUseCase
import cy.volleybolley.auth.domain.api.usecase.ClearAllLoginDataUseCase
import cy.volleybolley.auth.domain.api.usecase.GetAuthenticatedStatusUseCase
import cy.volleybolley.auth.domain.api.usecase.GetIsRegisteredUseCase
import cy.volleybolley.auth.domain.api.usecase.GetPersonalDataUseCase
import cy.volleybolley.auth.domain.api.usecase.GetRefreshTokenTimestampUseCase
import cy.volleybolley.auth.domain.api.usecase.GetRefreshTokenUseCase
import cy.volleybolley.auth.domain.api.usecase.GoogleTokenAuthUseCase
import cy.volleybolley.auth.domain.api.usecase.SavePersonalDataUseCase
import cy.volleybolley.auth.domain.impl.usecase.CheckRefreshTokenExpirationUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.ClearAllLoginDataUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.GetAuthenticatedStatusUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.GetIsRegisteredUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.GetPersonalDataUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.GetRefreshTokenTimestampUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.GetRefreshTokenUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.GoogleTokenAuthUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.SavePersonalDataUseCaseImpl
import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.di.HttpClientQualifier
import cy.volleybolley.core.di.PrefsQualifier
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val authorizationModule = module {
    // Prefs
    single(PrefsQualifier.ENCRYPTED_TOKENS.qualifier) {
        get<Context>().getSharedPreferences(PrefsQualifier.ENCRYPTED_TOKENS.fileName, Context.MODE_PRIVATE)
    }
    single(PrefsQualifier.USER.qualifier) {
        get<Context>().getSharedPreferences(PrefsQualifier.USER.fileName, Context.MODE_PRIVATE)
    }
    single(PrefsQualifier.REFRESH_TOKEN_TIMESTAMP.qualifier) {
        get<Context>().getSharedPreferences(PrefsQualifier.REFRESH_TOKEN_TIMESTAMP.fileName, Context.MODE_PRIVATE)
    }
    // End prefs

    // Storage
    single<TokenStorage> {
        TokenStorageImpl(get(PrefsQualifier.ENCRYPTED_TOKENS.qualifier))
    }
    single<UserStorage> {
        UserStorageImpl(get(PrefsQualifier.USER.qualifier), get())
    }
    single<RefreshTokenTimestampStorage> {
        RefreshTokenTimestampStorageImpl(get(PrefsQualifier.REFRESH_TOKEN_TIMESTAMP.qualifier))
    }
    // End storage

    // Repository
    single<LoginDataRepository> {
        LoginDataRepositoryImpl(get(), get(), get())
    }
    factory<AuthRepository> { AuthRepositoryImpl(get(named(HttpClientQualifier.AUTH.value))) }
    // End repository

    // use case
    single<GetAuthenticatedStatusUseCase> { GetAuthenticatedStatusUseCaseImpl(get()) }
    single<GetRefreshTokenUseCase> { GetRefreshTokenUseCaseImpl(get()) }
    single<GetRefreshTokenTimestampUseCase> { GetRefreshTokenTimestampUseCaseImpl(get()) }
    single<GetIsRegisteredUseCase> { GetIsRegisteredUseCaseImpl(get()) }
    single<CheckRefreshTokenExpirationUseCase> { CheckRefreshTokenExpirationUseCaseImpl(get()) }
    single<SavePersonalDataUseCase> { SavePersonalDataUseCaseImpl(get()) }
    single<GetPersonalDataUseCase> { GetPersonalDataUseCaseImpl(get()) }
    single<ClearAllLoginDataUseCase> { ClearAllLoginDataUseCaseImpl(get()) }
    single<GoogleTokenAuthUseCase> { GoogleTokenAuthUseCaseImpl(get(), get()) }
    // End use case

    // Network
    single<NetworkClient<AuthRequest, AuthResponse>>(HttpClientQualifier.AUTH.qualifier) {
        AuthNetworkClient(lazyHttpClient = inject())
    }
    // End network

    // ViewModel
    viewModel {
        AuthorizationViewModel(get())
    }
    // End viewModel
}
