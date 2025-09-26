package cy.volleybolley.core.presentation.ui.screens.home.home

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.courts.domain.model.Location

data class HomeScreenState(
    val nearGamesCount: Int = 0,
    val location: Location = Location(
        longitude = 7.866269,
        latitude = 98.396756,
        courtName = "Phuket Municipal Stadium",
        locationName = "Mueang Phuket District"
    )
) : UiState
