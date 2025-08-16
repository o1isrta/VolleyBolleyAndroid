package cy.volleybolley.core.di

import org.koin.core.qualifier.named

enum class HttpClientQualifier(val value: String) {
    COURTS("courts"),
    PROFILE("profile");

    val qualifier get() = named(value)
}
