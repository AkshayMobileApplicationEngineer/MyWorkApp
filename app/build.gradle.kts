plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" // Ensure you are applying KSP if needed
    kotlin("kapt") // Apply the KAPT plugin
}

android {
    namespace = "com.teampanlogic"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.teampanlogic"
        minSdk = 24 // Make sure this matches your project's requirements
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.checkout) // Razorpay
    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0") // Use kapt for Glide

    // Room dependencies
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    // Coroutines dependencies
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0") // Adjust versions as needed
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
}
