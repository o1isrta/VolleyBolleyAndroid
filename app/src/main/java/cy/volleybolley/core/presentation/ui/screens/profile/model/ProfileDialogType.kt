package cy.volleybolley.core.presentation.ui.screens.profile.model

enum class ProfileDialogType(
    val dialogText: String,
    var action: () -> Unit = {},
) {
    LOGOUT("Are you sure you want to log out of your account?"),
    DELETE("Are you sure you want to delete your account?");

    fun setPositiveAction(newAction: () -> Unit) {
        action = newAction
    }
}
