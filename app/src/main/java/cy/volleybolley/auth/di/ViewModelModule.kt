package cy.volleybolley.auth.di

import com.google.firebase.auth.FirebaseAuth
import cy.volleybolley.auth.ui.phone.PhoneAuthHelper
import cy.volleybolley.auth.ui.presentation.AuthViewModel
import cy.volleybolley.auth.ui.phone.PhoneAuthViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authViewModelModule = module {
    single { FirebaseAuth.getInstance() }
    single { PhoneAuthHelper(get()) }
    viewModel {
        AuthViewModel()
    }

    viewModel {
        PhoneAuthViewModel(get())
    }
}
