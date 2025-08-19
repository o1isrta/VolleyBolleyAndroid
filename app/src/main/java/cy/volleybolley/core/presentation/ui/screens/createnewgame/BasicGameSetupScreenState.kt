package cy.volleybolley.core.presentation.ui.screens.createnewgame

import cy.volleybolley.courts.domain.model.Court
import java.util.Date

data class BasicGameSetupScreenState(
    val message: String = "",
    val placeCourt: Court,
    val date: Date,
    val startTime: String,
    val endTime: String,
    val gender: String,
    val levels: Array<Int>
)
