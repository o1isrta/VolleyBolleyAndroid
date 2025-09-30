package cy.volleybolley.core.presentation.ui.model.state

import cy.volleybolley.core.presentation.ui.model.state.data.DialogData

data class MainActivityState(
    val isReady: Boolean = false,
    val screen: String? = null,
    val eventId: Int? = null,
    val notificationPermissionGranted: Boolean? = null,
    val globalDialog: DialogData? = null
)
