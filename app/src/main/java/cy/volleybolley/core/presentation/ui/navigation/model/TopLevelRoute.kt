package cy.volleybolley.core.presentation.ui.navigation.model

import androidx.compose.ui.graphics.painter.Painter
import cy.volleybolley.core.presentation.ui.navigation.NavMap

data class TopLevelRoute<T : NavMap>(
    val name: String,
    val route: T,
    val icon: Painter,
    val iconSelected: Painter,
)
