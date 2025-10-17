package cy.volleybolley.core.di

import org.koin.core.qualifier.named

enum class HttpClientQualifier(val value: String) {
    COURTS("courts"),
    GAMES("games"),
    TOURNAMENTS("tournaments"),
    PROFILE("profile"),
    PLAYERS("players"),
    REFERENCE_DATA("reference_data"),
    AUTH("auth");

    val qualifier get() = named(value)
}
