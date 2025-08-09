package cy.volleybolley.core.di

import org.koin.core.qualifier.named

enum class HttpClientQualifier(val value: String) {
    COURTS("courts"),
    DEVICE_TOKEN("device_token");
    PROFILE("profile");

    val qualifier get() = named(value)
}
