
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.expenseease"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.expenseease"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("com.google.code.gson:gson:2.8.8")
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.junit)
    testImplementation(libs.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.rules)

    // Optional: If you're testing intents
    androidTestImplementation(libs.espresso.intents)

    // Optional: If you're testing with RecyclerView
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // JUnit and Hamcrest for assertions
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("org.hamcrest:hamcrest-library:2.2")

    // Optional: Mockito for mocking
    androidTestImplementation("org.mockito:mockito-core:3.9.0")
    // JUnit for testing
    testImplementation(libs.junit)

    // For AndroidX JUnit
    androidTestImplementation(libs.androidx.junit.v113)

    // If you need to deal with asynchronous UI changes
    androidTestImplementation(libs.androidx.espresso.idling.resource)
    testImplementation(libs.robolectric)


}
