plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    id("kotlinx-serialization")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

android {
    namespace = "kz.magnum.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "kz.magnum.app"
        minSdk = 24
        targetSdk = 34
        versionCode = 3001
        versionName = "3.0.01"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    flavorDimensions += "flavor"

    productFlavors {
        create("prod") {
            //applicationIdSuffix = ""
            dimension = "flavor"
            //signingConfig signingConfigs.release
            val prodUrl: String by project // Defined in 'gradle.properties', which is not included in git.
            buildConfigField("String", "BASE_URL", prodUrl)
        }
        create("huawei") {
            //applicationIdSuffix = ""
            dimension = "flavor"
            //signingConfig signingConfigs.release
            val prodUrl: String by project // Defined in 'gradle.properties', which is not included in git.
            buildConfigField("String", "BASE_URL", prodUrl)
        }
        create("staging") {
            applicationIdSuffix = ".staging"
            dimension = "flavor"
            //signingConfig signingConfigs.release
            val devUrl: String by project // Defined in 'gradle.properties', which is not included in git. 
            buildConfigField("String", "BASE_URL", devUrl)
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    val composeVersion = "1.6.0-beta03"
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.0-beta03")
    // Jetpack Compose
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.1.2")

    val composeVer = "1.5.4"
    implementation("androidx.compose.ui:ui-util:$composeVer")
    implementation("androidx.compose.foundation:foundation:$composeVer")
    implementation("androidx.compose.foundation:foundation-layout:$composeVer")

    implementation("androidx.compose.material3:material3:1.2.0-beta01")
    //implementation("androidx.compose.material3:material3-window-size-class")
    implementation("androidx.compose.material:material-icons-extended:1.5.4")

    val lifecycleVersion = "2.6.2"
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")

    // Navigation for compose
    val navVersion = "2.7.6"
    implementation("androidx.navigation:navigation-compose:$navVersion")

    //Ktor dependencies
    val ktorVersion = "2.3.6"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    // HTTP engine: The HTTP client used to perform network requests.
    implementation("io.ktor:ktor-client-android:$ktorVersion")
    // The serialization engine used to convert objects to and from JSON.
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    // Logging
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    // Negotiation
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    // Json serialization
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    // Authentication
    implementation("io.ktor:ktor-client-auth:$ktorVersion")

    // Data store
    val datastoreVersion = "1.1.0-alpha07"
    implementation("androidx.datastore:datastore-preferences:$datastoreVersion")

    // Room
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    // To use Kotlin annotation processing tool (kapt)
    //kapt("androidx.room:room-compiler:$roomVersion")
    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$roomVersion")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")
    // optional - RxJava2 support for Room
    //implementation("androidx.room:room-rxjava2:$room_version")
    // optional - RxJava3 support for Room
    // implementation("androidx.room:room-rxjava3:$room_version")
    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation("androidx.room:room-guava:$roomVersion")
    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$roomVersion")
    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:$roomVersion")

    // KOIN
    val koinVersion= "3.5.0"
    // Koin Core features
    implementation("io.insert-koin:koin-core:$koinVersion")
    // Koin Test features
    testImplementation("io.insert-koin:koin-test:$koinVersion")
    // Koin for JUnit 4
    testImplementation("io.insert-koin:koin-test-junit4:$koinVersion")
    // Koin for JUnit 5
    testImplementation("io.insert-koin:koin-test-junit5:$koinVersion")

    // Koin main features for Android
    val koinAndroidVersion= "3.5.0"
    implementation("io.insert-koin:koin-android:$koinAndroidVersion")
    // Java Compatibility
    implementation("io.insert-koin:koin-android-compat:$koinAndroidVersion")
    // Jetpack WorkManager
    implementation("io.insert-koin:koin-androidx-workmanager:$koinAndroidVersion")
    // Navigation Graph
    implementation("io.insert-koin:koin-androidx-navigation:$koinAndroidVersion")
    // Koin for compose
    val koinAndroidComposeVersion= "3.5.0"
    implementation("io.insert-koin:koin-androidx-compose:$koinAndroidComposeVersion")

    // SMS / OTP
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.google.android.gms:play-services-auth-api-phone:18.0.1")

    implementation("com.google.firebase:firebase-messaging-ktx:23.4.0")

    // UI libs
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.25.1")
    val coilVersion = "2.5.0"
    implementation("io.coil-kt:coil-compose:$coilVersion")
    implementation("io.coil-kt:coil-svg:$coilVersion")

    implementation("com.google.zxing:core:3.5.2")

    // This lib enables usage of LocalDateTime in SDK < 26
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

    // For scanner of barcodes and QRs
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")
    val cameraVersion = "1.3.1"
    implementation("androidx.camera:camera-camera2:$cameraVersion")
    implementation("androidx.camera:camera-lifecycle:$cameraVersion")
    implementation("androidx.camera:camera-view:$cameraVersion")
    implementation("com.google.mlkit:barcode-scanning:17.2.0")

    // Biometric
    implementation("androidx.biometric:biometric:1.2.0-alpha05")

    // My libs
    implementation("aab.libraries.set:commons:1.0.6")
    implementation("aab.libraries.set:scannerview:1.0.1")
    implementation("aab.libraries.set:datetimewheel:1.0.0")
    // This lib is necessary for date-time wheel picker
    implementation("dev.chrisbanes.snapper:snapper:0.3.0")
}