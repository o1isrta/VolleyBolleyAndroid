package cy.volleybolley.core.di

import org.koin.core.qualifier.named

enum class HttpClientQualifier(val value: String) {
    COURTS("courts"),
    GAMES("games"),
    TOURNAMENTS("tournaments"),
    PROFILE("profile"),
    PLAYERS("players"),
    REFERENCE_DATA("reference_data"),
    AUTH("auth"),
    REGISTRATION("registration"),
    NO_ACCESS_TOKEN("no_access_token"),
    DEVICE_TOKEN("device_token"),
    NOTIFICATIONS("notifications");

    val qualifier get() = named(value)
}

enum class PrefsQualifier(val value: String) {
    ENCRYPTED("encrypted"),      // For tokens - secure storage
    USER("user");              // For user data - regular storage

    val qualifier get() = named(value)
}
