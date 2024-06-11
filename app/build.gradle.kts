import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.mimo.android"
    compileSdk = 34

    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())
    val clientId = properties["NAVER_CLIENT_ID"] ?: ""
    val clientSecret = properties["NAVER_CLIENT_SECRET"] ?: ""
    val accessTokenKey = properties["ACCESS_TOKEN_KEY"] ?: ""
    val refreshTokenKey = properties["REFRESH_TOKEN_KEY"] ?: ""
    val dataStoreName = properties["DATASTORE_NAME"] ?: ""
    val naverMapClientKey = properties["NAVER_MAP_CLIENT_KEY"]

    defaultConfig {
        applicationId = "com.mimo.android"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "CLIENT_ID", "$clientId")
        buildConfigField("String", "CLIENT_SECRET", "$clientSecret")
        buildConfigField("String", "ACCESS_TOKEN_KEY", "$accessTokenKey")
        buildConfigField("String", "REFRESH_TOKEN_KEY", "$refreshTokenKey")
        buildConfigField("String", "DATASTORE_NAME", "$dataStoreName")
        buildConfigField("String", "DATASTORE_NAME", "$naverMapClientKey")
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
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }

}

dependencies {

    //androidx
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.databinding:databinding-common:8.3.2")
    implementation("androidx.legacy:legacy-support-core-utils:1.0.0")
    implementation("androidx.browser:browser:1.4.0")
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
    implementation("androidx.core:core-ktx:1.3.0")
    implementation("androidx.fragment:fragment-ktx:1.3.6")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.1")

    // test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    // hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    implementation("androidx.hilt:hilt-navigation-fragment:1.2.0")

    // retrofit,
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //okHttpClient
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    // gilde
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

    // timber
    implementation("com.jakewharton.timber:timber:4.7.1")

    // tedPermission
    implementation("io.github.ParkSangGwon:tedpermission-normal:3.3.0")
    implementation("io.github.ParkSangGwon:tedpermission-coroutine:3.3.0")

    // naver oauth aar
    implementation(files("libs/oauth-5.9.1.aar"))
    implementation("com.airbnb.android:lottie:3.1.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.21")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.datastore:datastore-preferences-core:1.1.0")

    // naverMap
    implementation("com.naver.maps:map-sdk:3.18.0")

    // gms
    implementation("com.google.android.gms:play-services-location:21.2.0")

    // Media3 ExoPlayer
    implementation("androidx.media3:media3-exoplayer:1.3.1")
    implementation("androidx.media3:media3-exoplayer-dash:1.3.1")
    implementation("androidx.media3:media3-ui:1.3.1")

    // FlowLayout
    implementation("com.google.android.flexbox:flexbox:3.0.0")
}
