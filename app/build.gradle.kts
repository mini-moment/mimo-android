import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.google.service)
    alias(libs.plugins.hilt)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.mimo.minimoment"
    compileSdk = 34

    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())
    val clientId = properties["NAVER_CLIENT_ID"] ?: ""
    val clientSecret = properties["NAVER_CLIENT_SECRET"] ?: ""
    val dataStoreName = properties["DATASTORE_NAME"] ?: ""
    val naverMapClientKey = properties["NAVER_MAP_CLIENT_KEY"]
    val mimoServerUrl = properties["MIMO_SERVER_URL"] ?: ""
    val keystorePropertiesFile = rootProject.file("keystore.properties")
    val keystoreProperties = Properties()
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))

    defaultConfig {
        applicationId = "com.mimo.minimoment"
        minSdk = 26
        targetSdk = 34
        versionCode = 2
        versionName = "1.0.0"
        buildConfigField("String", "CLIENT_ID", "$clientId")
        buildConfigField("String", "CLIENT_SECRET", "$clientSecret")
        buildConfigField("String", "DATASTORE_NAME", "$dataStoreName")
        buildConfigField("String", "MIMO_SERVER_URL", "$mimoServerUrl")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
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
    implementation(libs.androidx.hilt.navigation.fragment)
    // retrofit,
    implementation(libs.converter.gson)
    // okHttpClient
    implementation(libs.bundles.network)
    // datastore
    implementation(libs.datastore.preferences)
    // timber
    implementation(libs.timber)
    implementation(platform(libs.firebase.bom))
    // naver
    implementation(libs.naver.maps)
}
