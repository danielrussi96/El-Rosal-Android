@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.app.elrosal"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.app.elrosal"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {


    implementation(libs.core.ktx)
    implementation(libs.bundles.lifecycle)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.bundles.shared.repository)
    implementation(libs.coil)
    implementation(libs.navigation.compose)
    implementation(libs.lottie)
    implementation(libs.bundles.koin)
    implementation(libs.splashscreen)
    implementation(libs.paging.compose)

    
    testImplementation(libs.bundles.koin.test)
    testImplementation(libs.junit)
    testImplementation(libs.mockito)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.coroutines)
    testImplementation(libs.turbine)
    testImplementation(project(":testSharedApp"))
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(project(":testSharedApp"))
    androidTestImplementation(libs.coroutines)
    androidTestImplementation(libs.bundles.koin.test)
    androidTestImplementation(libs.compose.test)
    androidTestImplementation(libs.mockwebserver)
    androidTestImplementation(libs.ktor.client.mock)
    androidTestImplementation(libs.mockito)
    androidTestImplementation(libs.mockito.inline)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    debugImplementation(libs.compose.manifest)
}