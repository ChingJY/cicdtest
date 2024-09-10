buildscript {
    val propertiesFile = File("${File(System.getProperty("user.dir")).parent}/github.properties")
    val properties = java.util.Properties()
    if (propertiesFile.exists()) properties.load(java.io.FileInputStream(propertiesFile))
    val githubUserName = properties.getProperty("GITHUB_USERNAME") ?: System.getenv("GITHUB_USERNAME")
    val githubAccessToken = properties.getProperty("GITHUB_ACCESS_TOKEN") ?: System.getenv("GITHUB_ACCESS_TOKEN")

    repositories {
        mavenLocal()
        maven { url = uri("https://maven.pkg.github.com/voicetube/android-sdk-core-plugin"); credentials { username = githubUserName; password = githubAccessToken } }
    }
    dependencies {
        classpath("com.voicetube.sdk:core-plugin:1.0.36")
    }
}

plugins {
    `kotlin-dsl`
}

apply(plugin = "voicetube-plugin-core")

repositories {
    mavenCentral()
    mavenLocal()
    google()
//    jcenter()
}

dependencies {
    implementation("com.android.tools.build:gradle:${findProperty("buildGradleVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${findProperty("kotlinVersion")}")
    implementation(gradleApi())
    implementation(localGroovy())
}

