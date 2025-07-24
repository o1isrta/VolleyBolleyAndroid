plugins {
    `kotlin-dsl`
}

group = "cy.volleybolley.buildlogic"

dependencies {
    // workaround for https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
