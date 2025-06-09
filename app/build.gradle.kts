import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    kotlin("kapt")
}

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) file.inputStream().use { load(it) }
}


android {
    namespace = "com.mtabarkevych.mymovie"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mtabarkevych.mymovie"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "MOVIE_API_KEY", localProperties["MOVIE_API_KEY"] as String)
    }
    buildFeatures {
        buildConfig = true
    }
    packaging {
        resources {
            excludes += setOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE.txt",
                "META-INF/LICENSE-notice.md",
                "META-INF/LICENSE",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt"
            )
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
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
    implementation(libs.androidx.paging.common.android)
    implementation(libs.core.ktx)
    implementation(libs.androidx.junit.ktx)

    debugImplementation(libs.androidx.ui.tooling)

    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.core)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.androidx.room.paging)

    implementation(libs.paging.runtime)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.paging.compose)

    implementation(libs.threetenabp)

    implementation(libs.coil.compose)

    // Unit Tests
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.robolectric)
    testImplementation(kotlin("test"))
    testImplementation(libs.ktor.client.mock)
    testImplementation (libs.kotlinx.coroutines.test.v173)
    testImplementation ("app.cash.turbine:turbine:1.0.0")
    testImplementation (libs.mockk)

    // Android Tests
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.androidx.junit.v121)
    androidTestImplementation(libs.androidx.core)
    androidTestImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug Test
    debugImplementation(libs.androidx.ui.test.manifest)

}