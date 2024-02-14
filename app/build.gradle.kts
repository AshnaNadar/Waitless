plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.waitless"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.waitless"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = VERSION_1_8
        targetCompatibility = VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    packaging {
        resources.excludes.add("META-INF/*")
    }

    val kotlinxSerializationVersion: String by project

    dependencies {
        // Compose
        implementation("androidx.activity:activity-compose:1.8.2")
        implementation("androidx.compose.ui:ui:1.6.1")
        implementation("androidx.compose.material:material:1.6.1")
        implementation("androidx.compose.ui:ui-tooling:1.6.1")

        // Material3
        implementation("androidx.compose.material3:material3:1.2.0")
        implementation("androidx.compose.material3:material3-window-size-class:1.2.0")
        implementation("androidx.compose.material3:material3-adaptive:1.0.0-alpha06")
        implementation("androidx.compose.material3:material3-adaptive-navigation-suite:1.0.0-alpha03")

        implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")
        implementation("androidx.core:core-ktx:1.12.0")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.11.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
        implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    }
}