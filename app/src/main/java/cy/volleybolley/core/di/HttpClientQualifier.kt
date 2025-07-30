package cy.volleybolley.core.di

import org.koin.core.qualifier.named

enum class HttpClientQualifier(val value: String) {
    COURTS("courts"),
    GAMES("games");

    val qualifier get() = named(value)
}
