package cy.volleybolley.core.di

import org.koin.core.qualifier.named

enum class HttpClientQualifier(val value: String) {
    COURTS("courts"),
    PROFILE("profile"),
    PLAYERS("players"),
    REFERENCE_DATA("reference_data");

    val qualifier get() = named(value)
}
