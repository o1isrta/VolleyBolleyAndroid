package cy.volleybolley.core.presentation.ui.model.state

import cy.volleybolley.core.presentation.ui.model.state.data.DialogData

data class MainActivityState(
    val isReady: Boolean = false,
    val screen: String? = null,
    val gameId: String? = null,
    val notificationPermissionGranted: Boolean? = null,
    val globalDialog: DialogData? = null
)
