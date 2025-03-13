plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.exchangegraph"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.exchangegraph"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    // ✅ Jetpack Compose BOM (para gestionar versiones automáticamente)
    implementation(platform("androidx.compose:compose-bom:2024.03.00"))

    // ✅ Dependencias esenciales de Jetpack Compose
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // ✅ ViewModel para Jetpack Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // ✅ Foundation (para LazyColumn, Canvas, etc.)
    implementation("androidx.compose.foundation:foundation")

    // ✅ Runtime para `remember`, `LaunchedEffect`, etc.
    implementation("androidx.compose.runtime:runtime")

    // ✅ Core de AndroidX y Jetpack Compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // ✅ Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Librería para gráficas
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // Para manejo de fechas
    implementation ("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    implementation(platform("androidx.compose:compose-bom:2024.03.00"))

}
