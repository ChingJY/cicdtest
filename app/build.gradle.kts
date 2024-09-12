import java.io.FileInputStream
import java.util.Properties

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
    id("com.google.firebase.appdistribution")
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

    val keystorePropertiesFile = rootProject.file("${rootDir.parent}/signature-voicetube.properties")
    val keystoreProperties = Properties()
    if (keystorePropertiesFile.exists()) {
        keystoreProperties.load(FileInputStream(keystorePropertiesFile))
    } else {
        keystoreProperties.setProperty("STORE_PASSWORD", System.getenv("STORE_PASSWORD"))
        keystoreProperties.setProperty("KEY_ALIAS", System.getenv("KEY_ALIAS"))
        keystoreProperties.setProperty("KEY_PASSWORD", System.getenv("KEY_PASSWORD"))
    }

    signingConfigs {
        create("release") {
            storeFile = file(System.getenv("KEYSTORE_FILE") ?: file("${rootDir.parent}/keystore-voicetube.keystore"))
            storePassword = keystoreProperties["STORE_PASSWORD"] as? String ?: ""
            keyAlias = keystoreProperties["KEY_ALIAS"] as? String ?: ""
            keyPassword = keystoreProperties["KEY_PASSWORD"] as? String ?: ""
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            firebaseAppDistribution {
                artifactType = "APK"
                releaseNotesFile = "commit.txt"
                groups = "QA-Team"
                serviceCredentialsFile = "firebase-app-distribution-credential.json"
            }
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