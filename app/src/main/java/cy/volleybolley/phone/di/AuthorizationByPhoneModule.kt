package cy.volleybolley.phone.di

import com.google.firebase.auth.FirebaseAuth
import cy.volleybolley.phone.domain.PhoneTokenAuthUseCase
import cy.volleybolley.phone.domain.impl.PhoneTokenAuthUseCaseImpl
import cy.volleybolley.phone.ui.PhoneAuthHelper
import cy.volleybolley.phone.ui.presentation.AuthorizationByPhoneViewModel
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
        PhoneTokenAuthUseCaseImpl(get())
    }

    viewModel {
        AuthorizationByPhoneViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}
