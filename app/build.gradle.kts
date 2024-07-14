import java.util.Properties

plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.mimo.android"
    compileSdk = 34

    packagingOptions {
        exclude("META-INF/gradle/incremental.annotation.processors")
    }

    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())
    val clientId = properties["NAVER_CLIENT_ID"] ?: ""
    val clientSecret = properties["NAVER_CLIENT_SECRET"] ?: ""
    val dataStoreName = properties["DATASTORE_NAME"] ?: ""
    val naverMapClientKey = properties["NAVER_MAP_CLIENT_KEY"]
    val mimoServerUrl = properties["MIMO_SERVER_URL"] ?: ""

    defaultConfig {
        applicationId = "com.mimo.android"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "CLIENT_ID", "$clientId")
        buildConfigField("String", "CLIENT_SECRET", "$clientSecret")
        buildConfigField("String", "DATASTORE_NAME", "$dataStoreName")
        buildConfigField("String", "MIMO_SERVER_URL", "$mimoServerUrl")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            manifestPlaceholders["NAVER_MAP_CLIENT_KEY"] = naverMapClientKey as String
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
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

    // androidx
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    // test
    testImplementation(libs.junit)
    testImplementation(libs.espresso.core)

    // module
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":presentation"))
    implementation(project(":libs"))
    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation("androidx.hilt:hilt-navigation-fragment:1.2.0")
    // retrofit,
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // okHttpClient
    implementation(libs.bundles.network)
    // datastore
    implementation(libs.datastore.preferences)
    // timber
    implementation(libs.timber)
    // naver
    implementation(libs.naver.maps)
}
