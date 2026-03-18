package cy.volleybolley.core.di

import org.koin.core.qualifier.named

enum class PrefsQualifier(val value: String, val fileName: String) {
    ENCRYPTED_TOKENS("encrypted", "token_prefs"),

    USER("user", "user_prefs");

    val qualifier get() = named(value)
}
