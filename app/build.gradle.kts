plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.serialization)
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
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources =  true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "release-proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = true
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // DI (Koin)
    implementation(libs.koin.androidx.compose)

    // NETWORK
    implementation(libs.bundles.ktor.client)

}