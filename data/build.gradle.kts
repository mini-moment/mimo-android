import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    kotlin("kapt")
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())
android {
    namespace = "com.mimo.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        buildConfigField("String", "ACCESS_TOKEN_KEY", properties["ACCESS_TOKEN_KEY"] as String)
        buildConfigField(
            "String",
            "REFRESH_TOKEN_KEY",
            properties["REFRESH_TOKEN_KEY"] as String,
        )
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.bundles.hilt)
    implementation(libs.bundles.network)
    implementation(libs.datastore.preferences)
    implementation(libs.junit)
    implementation(project(":domain"))
}
