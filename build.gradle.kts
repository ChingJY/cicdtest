// Top-level build file where you can add configuration options common to all sub-projects/modules.
//plugins {
//    alias(libs.plugins.android.application) apply false
//    alias(libs.plugins.jetbrains.kotlin.android) apply false
//    alias(libs.plugins.android.library) apply false
//}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    buildCoreConfig()
    buildCredentials(
        githubPropertiesPath = "${rootDir.parent}/github.properties",
        keyStorePropertiesPath = "${rootDir.parent}/signature-voicetube.properties",
        huaweiPublishingPropertiesPath = "${rootDir.parent}/huawei-publishing-voicetube.properties"
    )

    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven { url = uri("https://maven.pkg.github.com/voicetube/android-ndk-plugin"); credentials { username = CoreCredentials.GitHub.userName; password = CoreCredentials.GitHub.accessToken } }
        maven { url = uri("https://maven.pkg.github.com/voicetube/android-huawei-publishing-plugin"); credentials { username = CoreCredentials.GitHub.userName; password = CoreCredentials.GitHub.accessToken } }
    }
    dependencies {
        classpath("com.google.gms:google-services:4.4.2")
        classpath(coreDependencyOf("com.voicetube.plugin:huawei-publishing"))
        classpath(coreDependencyOf("com.voicetube.plugin:ndk"))
        classpath(coreDependencyOf("com.voicetube.plugin:cicd"))
        classpath(coreDependencyOf("io.realm:realm-gradle-plugin"))
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://jitpack.io")
        maven { url = uri("https://maven.pkg.github.com/voicetube/android-sdk-core"); credentials { username = CoreCredentials.GitHub.userName; password = CoreCredentials.GitHub.accessToken } }
        jcenter()
    }
}

task<Delete>("clean") { delete(rootProject.buildDir) }