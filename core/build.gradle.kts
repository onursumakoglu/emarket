plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id ("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.okamiko.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    val fragmentVersion = "1.8.7"
    val navVersion = "2.9.0"
    val koinVersion = "3.2.0"
    val retrofitVersion = "2.9.0"
    val okhttpVersion = "5.0.0-alpha.2"
    val roomVersion = "2.6.1"
    val glideVersion = "4.16.0"


    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    api("androidx.fragment:fragment-ktx:$fragmentVersion")
    api("androidx.navigation:navigation-fragment-ktx:$navVersion")
    api("androidx.navigation:navigation-ui-ktx:$navVersion")

    api("io.insert-koin:koin-android:$koinVersion")

    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttpVersion")

    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    api("com.github.bumptech.glide:glide:$glideVersion")
}