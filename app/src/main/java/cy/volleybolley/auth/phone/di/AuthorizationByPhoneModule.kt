package cy.volleybolley.auth.phone.di

import com.google.firebase.auth.FirebaseAuth
import cy.volleybolley.auth.phone.data.CurrentActivityProvider
import cy.volleybolley.auth.phone.data.FirebasePhoneAuthService
import cy.volleybolley.auth.phone.domain.PhoneAuthService
import cy.volleybolley.auth.phone.domain.PhoneTokenAuthUseCase
import cy.volleybolley.auth.phone.domain.impl.PhoneTokenAuthUseCaseImpl
import cy.volleybolley.auth.phone.ui.AuthorizationByPhoneViewModel
import cy.volleybolley.core.presentation.App
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authorizationByPhoneModule = module {
    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }

    // Use the pre-created provider from App to ensure lifecycle callbacks are registered early
    single<CurrentActivityProvider> {
        (androidApplication() as App).currentActivityProvider
    }

    single<PhoneAuthService> {
        FirebasePhoneAuthService(get(), get())
    }

    single<PhoneTokenAuthUseCase> {
        PhoneTokenAuthUseCaseImpl(get(), get())
    }

    viewModel {
        AuthorizationByPhoneViewModel(get(), get())
    }
}
