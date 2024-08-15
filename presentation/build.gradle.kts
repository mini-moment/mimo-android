import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.navigation.safe.args)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlinx.serialization)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("kotlin-kapt")
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())
android {
    namespace = "com.mimo.presentation"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        buildConfigField("String", "MIMO_SERVER_URL", properties["MIMO_SERVER_URL"] as String)
        buildConfigField(
            "String",
            "MIMO_VIDEO_BASE_URL",
            properties["MIMO_VIDEO_BASE_URL"] as String,
        )
        buildConfigField(
            "String",
            "MIMO_POST_THUMBNAIL_BASE_URL",
            properties["MIMO_POST_THUMBNAIL_BASE_URL"] as String,
        )
        consumerProguardFiles("consumer-rules.pro")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.bundles.android)
    implementation(libs.bundles.navigation)
    implementation(libs.timber)
    //naver oauth
    implementation(libs.naver.maps)
    implementation(libs.androidx.legacy.support.core.utils)
    implementation(libs.androidx.databinding.common)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.security.crypto)
    implementation(libs.lottie)
    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.fragment)
    //exoplayer
    implementation(libs.bundles.media3)
    //splash screen
    implementation(libs.splashscreen)
    //test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.play.services.location)
    implementation(libs.flex.box)
    implementation(libs.bundles.tedpermission)
    implementation(libs.dotsindicator)
    implementation(project(":libs"))
    implementation(project(":domain"))
}
