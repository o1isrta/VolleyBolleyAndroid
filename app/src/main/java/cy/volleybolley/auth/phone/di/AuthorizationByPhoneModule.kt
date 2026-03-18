package cy.volleybolley.auth.phone.di

import com.google.firebase.auth.FirebaseAuth
import cy.volleybolley.auth.phone.domain.PhoneTokenAuthUseCase
import cy.volleybolley.auth.phone.domain.impl.PhoneTokenAuthUseCaseImpl
import cy.volleybolley.auth.phone.ui.PhoneAuthHelper
import cy.volleybolley.auth.phone.ui.presentation.AuthorizationByPhoneViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authorizationByPhoneModule = module {
    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }

    single {
        PhoneAuthHelper(get())
    }

    single<PhoneTokenAuthUseCase> {
        PhoneTokenAuthUseCaseImpl(get(), get())
    }

    viewModel {
        AuthorizationByPhoneViewModel(get())
    }
}
