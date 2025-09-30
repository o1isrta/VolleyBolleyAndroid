package cy.volleybolley.core.presentation.ui.screens.home.home

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.courts.domain.model.Location

data class HomeScreenState(
    val nearGamesCount: Int = 0,
    val location: Location = Location(
        longitude = 0.0,
        latitude = 0.0,
        courtName = "No name",
        locationName = "Zero point"
    )
) : UiState
