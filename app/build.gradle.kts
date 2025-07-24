plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" // ✅ Agregado para KSP
}

android {
    namespace = "com.example.tripmanager"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tripmanager"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        compose = true // Habilita Jetpack Compose
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1" // Verifica si esta versión es la más actual
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // 🔹 Dependencias de AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Dependencias básicas de Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))  // Usar el BOM de Compose para manejar las versiones
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1") // Usa la versión más reciente
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.0")  // Para la vista previa de Compose
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")

    // Dependencias principales de Compose
    implementation("androidx.compose.ui:ui:1.5.0")  // Última versión estable
    implementation("androidx.compose.material3:material3:1.0.1")  // Para Material3
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0")  // Para vista previa

    // Dependencias adicionales que necesitas
    implementation("androidx.compose.foundation:foundation:1.5.0")  // Añadir esta línea
    implementation("androidx.compose.material:material:1.5.0")  // Si lo necesitas

    // Room Database (ORM)
    implementation("androidx.room:room-runtime:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")  // Asegúrate de incluir esta dependencia
    ksp("androidx.room:room-compiler:2.5.2")  // Esto es para la generación de código de Room

    // 🔹 LiveData para la arquitectura MVVM
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")

    // 🔹 Dependencias de pruebas
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("androidx.compose.runtime:runtime-livedata:1.5.0") // Añadir para Flow y State
    implementation("androidx.compose.runtime:runtime:1.5.0") // Añadir para Runtime
    implementation ("androidx.compose.material3:material3:1.0.0")


}

