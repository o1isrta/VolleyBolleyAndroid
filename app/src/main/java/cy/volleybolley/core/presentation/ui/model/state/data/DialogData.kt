package cy.volleybolley.core.presentation.ui.model.state.data

data class DialogData(
    val title: String,
    val message: String,
    val onConfirm: () -> Unit,
    val onDismiss: () -> Unit
)
