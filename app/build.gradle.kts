 import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    id ("dagger.hilt.android.plugin")
    id ("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
}

android {

    namespace = "com.example.investiq"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.investiq"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val properties = Properties().apply {
            load(project.rootProject.file("local.properties").inputStream())
        }
        val fmpApiKey = properties.getProperty("fmpApiKey")

        buildConfigField("String","FMP_API_KEY",fmpApiKey)

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug{
            applicationIdSuffix=".debug"
            isDebuggable = true
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation ("androidx.compose.material:material:1.7.7")
    implementation(libs.androidx.material3.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // OpenCSV
    implementation ("com.opencsv:opencsv:5.5.2")

    implementation ("com.google.accompanist:accompanist-flowlayout:0.17.0")

    implementation ("androidx.paging:paging-compose:3.3.0")
    implementation ("com.google.accompanist:accompanist-swiperefresh:0.24.2-alpha")


    // Coil
    implementation ("io.coil-kt:coil-compose:2.4.0")

    //Dagger - Hilt
    implementation ("com.google.dagger:hilt-android:2.49")
    ksp ("com.google.dagger:hilt-android-compiler:2.49")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.3")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.3")

    // Room
    implementation ("androidx.room:room-runtime:2.6.1")
    ksp ("androidx.room:room-compiler:2.6.1")

    // Kotlin Extensions and Coroutines support for Room
    implementation ("androidx.room:room-ktx:2.6.1")

    // system UI Controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")


    // Swipe item
    implementation ("me.saket.swipe:swipe:1.3.0")

    //lottie
    implementation("com.airbnb.android:lottie-compose:6.3.0")

    // haze effect
    implementation("dev.chrisbanes.haze:haze-jetpack-compose:0.4.1")

}