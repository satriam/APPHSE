plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.hseapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.hseapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.5.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-messaging:23.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // dependency for slider view
//    implementation ("com.github.smarteist:autoimageslider:1.4.0")
    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.2")
// dependency for loading image from url
    implementation ("com.github.bumptech.glide:glide:4.11.0")
    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
//retrofit gson
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("com.squareup.okhttp3:okhttp:4.10.0")

    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation ("com.toptoche.searchablespinner:searchablespinnerlibrary:1.3.1")
    implementation ("de.hdodenhof:circleimageview:2.2.0")
    implementation ("com.github.timonknispel:KTLoadingButton:1.2.0")
//    implementation ("com.github.StevenDXC:DxLoadingButton:2.4")
    implementation(platform("com.google.firebase:firebase-bom:32.4.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-inappmessaging-display-ktx")


}
