buildscript {
    repositories {
        maven("https://plugins.gradle.org/m2/")
    }
}

repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    flatDir {
        dirs("libs")
    }
}

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("voicetube-plugin-ndk")
    id("voicetube-plugin-huawei-publishing")
    id("com.plugin.cicd")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.cicdtest"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cicdtest"
        minSdk = 24
        targetSdk = 34
        versionCode = CoreConfig.App.versionCode
        versionName = CoreConfig.App.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file(System.getenv("KEYSTORE_FILE") ?: "keystore.jks")
            storePassword = System.getenv("KEYSTORE_PASSWORD")
            keyAlias = System.getenv("KEY_ALIAS")
            keyPassword = System.getenv("KEY_PASSWORD")
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
}