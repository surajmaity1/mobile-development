plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.codingwithmitch.googlemaps2018"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.codingwithmitch.googlemaps2018"
        minSdk = 24
        targetSdk = 33
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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    implementation(platform("com.google.firebase:firebase-bom:32.2.2"))
    implementation("com.google.firebase:firebase-analytics")
    // Firebase Core
    implementation("com.google.firebase:firebase-core:21.1.1")
    //Firebase Authentication
    implementation("com.google.firebase:firebase-auth:22.1.1")
    // Firestore Firestore
    implementation("com.google.firebase:firebase-firestore:24.7.0")



    // glide
    implementation("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")

    // Circle ImageView
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Support multidex
    implementation("com.android.support:multidex:1.0.3")
}