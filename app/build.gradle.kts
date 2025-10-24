import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.serialization)
    alias(libs.plugins.google.services)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "cy.volleybolley"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "cy.volleybolley"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val localProperties = Properties()
        val propertiesFile = File(rootDir, "local.properties")
        if (propertiesFile.exists()) {
            localProperties.load(propertiesFile.inputStream())
        } else {
            error("local.properties file not exists")
        }

        val serverUrl = localProperties.getProperty("SERVER_URL")
            ?: error("You should add SERVER_URL property in local.properties")
        buildConfigField("String", "BASE_URL", "\"$serverUrl\"")

        val mapsApiKey = localProperties.getProperty("MAPS_API_KEY")
            ?: error("You should add MAPS_API_KEY property in local.properties")
        buildConfigField("String", "MAPS_API_KEY", "\"$mapsApiKey\"")

        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey
    }

    signingConfigs {
        // Debug configuration with common keystore
        getByName("debug") {
            storeFile = rootProject.file("keystore/team-debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "release-proguard-rules.pro"
            )
        }
        debug {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "debug-proguard-rules.pro"
            )
        }
    }
    val javaVersion = libs.versions.javaVersion.get()
    compileOptions {
        JavaVersion.toVersion(javaVersion).let { version ->
            sourceCompatibility = version
            targetCompatibility = version
        }
    }
    kotlinOptions {
        jvmTarget = javaVersion
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.coil.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.bundles.koin.di)
    implementation(libs.bundles.ktor.client)
    implementation(libs.androidx.navigation.compose)

    ksp(libs.koin.ksp.compiler)

    implementation(libs.play.services.auth)
    implementation(libs.firebase.auth)
    implementation(platform(libs.firebase.bom))

    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation(libs.android.maps.utils)
    implementation(libs.accompanist.permissions)
    implementation(libs.maps.compose)
}
