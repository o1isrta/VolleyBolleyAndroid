package cy.volleybolley.auth.di

import com.google.firebase.auth.FirebaseAuth
import cy.volleybolley.auth.ui.PhoneAuthHelper
import cy.volleybolley.auth.ui.presentation.AuthViewModel
import cy.volleybolley.auth.ui.presentation.PhoneAuthViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authViewModelModule = module {
    single { FirebaseAuth.getInstance() }
    single { PhoneAuthHelper(get()) }
    single { PhoneAuthUiDelegate(get()) }
    viewModel {
        AuthViewModel()
    }

    viewModel {
        PhoneAuthViewModel()
    }
}
