package cy.volleybolley.core.presentation.ui.navigation.model

import cy.volleybolley.core.presentation.ui.navigation.AboutLevelsRoute
import cy.volleybolley.core.presentation.ui.navigation.ChangePhotoRoute
import cy.volleybolley.core.presentation.ui.navigation.LaunchRoute
import cy.volleybolley.core.presentation.ui.navigation.OnboardingRoute
import cy.volleybolley.core.presentation.ui.navigation.PersonalDataRoute
import cy.volleybolley.core.presentation.ui.navigation.RegistrationByPhoneRoute
import cy.volleybolley.core.presentation.ui.navigation.RegistrationRoute
import cy.volleybolley.core.presentation.ui.navigation.SignUpRoute

enum class NoBarsRoutes(val className: String, val noBottomBar: Boolean, val noTopBar: Boolean) {
    LAUNCH(LaunchRoute::class.qualifiedName.toString(), true, true),
    ONBOARDING(OnboardingRoute::class.qualifiedName.toString(), true, true),
    SIGNUP(SignUpRoute::class.qualifiedName.toString(), true, true),
    REGISTRATION(RegistrationRoute::class.qualifiedName.toString(), true, true),
    REGISTRATIONBYPHONE(RegistrationByPhoneRoute::class.qualifiedName.toString(), true, true),
    ABOUTLEVELS(AboutLevelsRoute::class.qualifiedName.toString(), true, true),
    PERSONALDATA(PersonalDataRoute::class.qualifiedName.toString(), false, true),
    CHANGEPHOTO(ChangePhotoRoute::class.qualifiedName.toString(), false, true);

    companion object {
        @JvmStatic
        fun showBottomBar(routeName: String): Boolean {
            if (routeName.isEmpty()) {
                return false
            }
            return entries.filter { it.noBottomBar }.find { routeName.contains(it.className) } == null
        }

        @JvmStatic
        fun showTopBar(routeName: String): Boolean {
            if (routeName.isEmpty()) {
                return false
            }
            return entries.filter { it.noTopBar }.find { routeName.contains(it.className) } == null
        }
    }
}
