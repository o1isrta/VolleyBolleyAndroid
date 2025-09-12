package cy.volleybolley.core.presentation.ui.navigation.model

import cy.volleybolley.core.presentation.ui.navigation.LaunchRoute
import cy.volleybolley.core.presentation.ui.navigation.OnboardingRoute

enum class NoBarsRoutes(val className: String, val noBottomBar: Boolean, val noTopBar: Boolean) {
    LAUNCH(LaunchRoute::class.qualifiedName.toString(), true, true),
    ONBOARDING(OnboardingRoute::class.qualifiedName.toString(), true, true);

    companion object {
        @JvmStatic
        fun showBottomBar(routeName: String): Boolean {
            return !NoBarsRoutes.entries.filter {it.noBottomBar}.map { it.className }.contains(routeName)
        }

        @JvmStatic
        fun showTopBar(routeName: String): Boolean {
            return !NoBarsRoutes.entries.filter {it.noTopBar}.map { it.className }.contains(routeName)
        }
    }
}
