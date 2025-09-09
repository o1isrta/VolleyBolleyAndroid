package cy.volleybolley.profile.presentation.ui.screens.enterpaymentdata.model

enum class EnterPaymentDialogType(
    val dialogText: String,
    var action: () -> Unit = {},
) {
    SUCCESS("Payment changes have been saved successfully");

    fun setDoneAction(newAction: () -> Unit) {
        action = newAction
    }
}
