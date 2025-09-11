package cy.volleybolley.notification.presentation.ui.model

enum class NotificationErrorType(val message: String) {
    NETWORK("Network error"),
    SERVER("Server error"),
    TIMEOUT("Timeout"),
    FIREBASE("Firebase error"),
    UNKNOWN("Unknown error")
}
