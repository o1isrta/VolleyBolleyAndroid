plugins {
    `kotlin-dsl`
    id("java-gradle-plugin")
}

group = "ru.practicum.android.diploma.plugins"

gradlePlugin {
    plugins {
        create("developPropertiesPlugin") {
            id = "ru.practicum.android.diploma.plugins.developproperties"
            implementationClass = "ru.practicum.android.diploma.plugins.developproperties.DevelopPropertiesPlugin"
        }
    }
}
