package cy.volleybolley.auth.di

import android.content.Context
import cy.volleybolley.auth.data.AuthRepositoryImpl
import cy.volleybolley.auth.data.LoginDataRepositoryImpl
import cy.volleybolley.auth.data.RefreshTokenTimestampRepositoryImpl
import cy.volleybolley.auth.data.network.AuthNetworkClient
import cy.volleybolley.auth.data.network.model.AuthRequest
import cy.volleybolley.auth.data.network.model.AuthResponse
import cy.volleybolley.auth.data.state.AuthStateHolderImpl
import cy.volleybolley.auth.data.storage.TokenStorageImpl
import cy.volleybolley.auth.data.storage.UserStorageImpl
import cy.volleybolley.auth.domain.api.AuthRepository
import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.auth.domain.api.RefreshTokenTimestampRepository
import cy.volleybolley.auth.domain.api.state.AuthStateHolder
import cy.volleybolley.auth.domain.api.storage.TokenStorage
import cy.volleybolley.auth.domain.api.storage.UserStorage
import cy.volleybolley.auth.domain.api.usecase.CheckRefreshTokenExpirationUseCase
import cy.volleybolley.auth.domain.api.usecase.ClearAllLoginDataUseCase
import cy.volleybolley.auth.domain.api.usecase.ClearTokensUseCase
import cy.volleybolley.auth.domain.api.usecase.GetAccessTokenUseCase
import cy.volleybolley.auth.domain.api.usecase.GetAuthenticatedStatusUseCase
import cy.volleybolley.auth.domain.api.usecase.GetIsRegisteredUseCase
import cy.volleybolley.auth.domain.api.usecase.GetPersonalDataUseCase
import cy.volleybolley.auth.domain.api.usecase.GetRefreshTokenTimestampUseCase
import cy.volleybolley.auth.domain.api.usecase.GetRefreshTokenUseCase
import cy.volleybolley.auth.domain.api.usecase.GoogleTokenAuthUseCase
import cy.volleybolley.auth.domain.api.usecase.RefreshAccessTokenUseCase
import cy.volleybolley.auth.domain.api.usecase.SaveAccessTokenUseCase
import cy.volleybolley.auth.domain.api.usecase.SaveIsRegisteredUseCase
import cy.volleybolley.auth.domain.api.usecase.SavePersonalDataUseCase
import cy.volleybolley.auth.domain.api.usecase.SaveRefreshTokenTimestampUseCase
import cy.volleybolley.auth.domain.api.usecase.SaveRefreshTokenUseCase
import cy.volleybolley.auth.domain.impl.usecase.CheckRefreshTokenExpirationUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.ClearAllLoginDataUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.ClearTokensUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.GetAccessTokenUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.GetAuthenticatedStatusUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.GetIsRegisteredUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.GetPersonalDataUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.GetRefreshTokenTimestampUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.GetRefreshTokenUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.GoogleTokenAuthUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.RefreshAccessTokenUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.SaveAccessTokenUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.SaveIsRegisteredUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.SavePersonalDataUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.SaveRefreshTokenTimestampUseCaseImpl
import cy.volleybolley.auth.domain.impl.usecase.SaveRefreshTokenUseCaseImpl
import cy.volleybolley.auth.chooseMethod.util.GoogleSignInHelper
import cy.volleybolley.auth.chooseMethod.AuthorizationViewModel
import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.di.HttpClientQualifier
import cy.volleybolley.core.di.PrefsQualifier
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val authorizationModule = module {
    // SharedPreferences for tokens (use EncryptedSharedPreferences in production)
    single(PrefsQualifier.ENCRYPTED_TOKENS.qualifier) {
        get<Context>().getSharedPreferences(PrefsQualifier.ENCRYPTED_TOKENS.fileName, Context.MODE_PRIVATE)
    }

    // Regular SharedPreferences for user data
    single(PrefsQualifier.USER.qualifier) {
        get<Context>().getSharedPreferences(PrefsQualifier.USER.fileName, Context.MODE_PRIVATE)
    }

    // Storage implementations
    single<TokenStorage> {
        TokenStorageImpl(get(PrefsQualifier.ENCRYPTED_TOKENS.qualifier))
    }
    single<UserStorage> {
        UserStorageImpl(get(PrefsQualifier.USER.qualifier), get())
    }
    single<RefreshTokenTimestampRepository> {
        RefreshTokenTimestampRepositoryImpl(get())
    }

    // Auth State Holder (StateFlows)
    single<AuthStateHolder> {
        val tokenStorage = get<TokenStorage>()
        val userStorage = get<UserStorage>()
        AuthStateHolderImpl(
            initialAuthenticated = tokenStorage.hasRefreshToken(),
            initialPersonalData = null // Will be loaded lazily
        )
    }

    // Repository
    single<LoginDataRepository> {
        LoginDataRepositoryImpl(get(), get(), get(), get())
    }

    // Authenticated status
    single<GetAuthenticatedStatusUseCase> { GetAuthenticatedStatusUseCaseImpl(get()) }

    // Token Use Cases
    single<SaveAccessTokenUseCase> { SaveAccessTokenUseCaseImpl(get()) }
    single<SaveRefreshTokenUseCase> { SaveRefreshTokenUseCaseImpl(get(), get()) }
    single<GetAccessTokenUseCase> { GetAccessTokenUseCaseImpl(get()) }
    single<GetRefreshTokenUseCase> { GetRefreshTokenUseCaseImpl(get()) }
    single<ClearTokensUseCase> { ClearTokensUseCaseImpl(get(), get(), get()) }

    // Timestamp Use Cases
    single<SaveRefreshTokenTimestampUseCase> { SaveRefreshTokenTimestampUseCaseImpl(get()) }
    single<GetRefreshTokenTimestampUseCase> { GetRefreshTokenTimestampUseCaseImpl(get()) }

    // Registration Status Use Cases
    single<SaveIsRegisteredUseCase> { SaveIsRegisteredUseCaseImpl(get()) }
    single<GetIsRegisteredUseCase> { GetIsRegisteredUseCaseImpl(get()) }

    // Refresh Token Use Cases
    single<RefreshAccessTokenUseCase> { RefreshAccessTokenUseCaseImpl(get(), get()) }
    single<CheckRefreshTokenExpirationUseCase> { CheckRefreshTokenExpirationUseCaseImpl(get()) }

    // PersonalData Use Cases
    single<SavePersonalDataUseCase> { SavePersonalDataUseCaseImpl(get(), get()) }
    single<GetPersonalDataUseCase> { GetPersonalDataUseCaseImpl(get()) }
    single<ClearAllLoginDataUseCase> { ClearAllLoginDataUseCaseImpl(get()) }

    // Network
    single<NetworkClient<AuthRequest, AuthResponse>>(HttpClientQualifier.AUTH.qualifier) {
        AuthNetworkClient(lazyHttpClient = inject(HttpClientQualifier.NO_ACCESS_TOKEN.qualifier))
    }

    factory<AuthRepository> { AuthRepositoryImpl(get(named(HttpClientQualifier.AUTH.value))) }
    single<GoogleTokenAuthUseCase> { GoogleTokenAuthUseCaseImpl(get(), get()) }

    single {
        GoogleSignInHelper(
            get()
        )
    }

    viewModel {
        AuthorizationViewModel(get())
    }
}
