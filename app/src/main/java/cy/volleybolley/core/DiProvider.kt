package cy.volleybolley.core

import cy.volleybolley.auth.di.authViewModelModule
import cy.volleybolley.core.di.coreModule
import cy.volleybolley.core.presentation.ui.screens.authorization.launch.di.launchModule
import cy.volleybolley.core.presentation.ui.screens.authorization.registration.di.registrationModule
import cy.volleybolley.core.presentation.ui.screens.authorization.signup.di.signUpModule
import cy.volleybolley.core.presentation.ui.screens.authorization.signupByPhone.di.signupByPhoneModule
import cy.volleybolley.courts.di.courtsModule
import cy.volleybolley.profile.di.profileModule
import cy.volleybolley.referencedata.di.referenceDataModule

object DiProvider {
    val modules = listOf(
        coreModule,
        courtsModule,
        authViewModelModule,
        referenceDataModule,
        profileModule,
        launchModule,
        signUpModule,
        registrationModule,
        signupByPhoneModule
    )
}
