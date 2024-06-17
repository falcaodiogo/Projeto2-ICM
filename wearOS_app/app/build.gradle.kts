plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.roborazzi)
    id("org.jetbrains.kotlin.kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "ua.deti.pt.wearosapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "ua.deti.pt.wearosapp"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // General compose dependencies
    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.ui.tooling.preview)

    // Compose for Wear OS Dependencies
    implementation(libs.wear.compose.material)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.wear)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui.tooling)

    // Foundation is additive, so you can use the mobile version in your Wear OS app.
    implementation(libs.wear.compose.foundation)

    implementation(libs.guava)
    implementation(libs.androidx.concurrent)

    // Wear OS Compose Navigation
    implementation(libs.compose.wear.navigation)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.horologist.compose.layout)
    implementation(libs.horologist.compose.material)
    implementation(libs.horologist.health.composables)
    implementation(libs.horologist.health.service)

    // Wear Health Services
    implementation(libs.androidx.health.services)

    // Lifecycle Components
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.common.java8)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.lifecycle.service)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.core.splashscreen)

    // Ongoing Activity
    implementation(libs.wear.ongoing.activity)

    // Hilt
    implementation(libs.hilt.navigation.compose)
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)

    coreLibraryDesugaring(libs.desugar.jdk.libs)
}