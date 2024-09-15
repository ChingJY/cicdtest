import java.io.File
import java.io.FileInputStream
import java.util.Properties

object CoreCredentials {
    object GitHub {
        var userName = ""
        var accessToken = ""
    }

    object Keystory {
        var storePassword = ""
        var keyAlias = ""
        var keyPassword = ""
    }

    object HuaweiPublishing {
        var appId = ""
        var clientId = ""
        var clientSecret = ""
    }

    object OneSignal {
        var apiKey = ""
    }

    fun build(githubPropertiesPath: String? = null, keyStorePropertiesPath: String? = null, huaweiPublishingPropertiesPath: String? = null, onesignalPropertiesPath: String? = null) {
        if (!githubPropertiesPath.isNullOrEmpty()) {
            val properties = getPropertiesFromFile(githubPropertiesPath)
            GitHub.userName = properties.getProperty("GITHUB_USERNAME") ?: System.getenv("GITHUB_USERNAME")
            GitHub.accessToken = properties.getProperty("GITHUB_ACCESS_TOKEN") ?: System.getenv("GITHUB_ACCESS_TOKEN")
        }
        if (!keyStorePropertiesPath.isNullOrEmpty()) {
            val properties = getPropertiesFromFile(keyStorePropertiesPath)
            Keystory.storePassword = properties.getProperty("STORE_PASSWORD") ?: System.getenv("STORE_PASSWORD")
            Keystory.keyAlias = properties.getProperty("KEY_ALIAS") ?: System.getenv("KEY_ALIAS")
            Keystory.keyPassword = properties.getProperty("KEY_PASSWORD") ?: System.getenv("KEY_PASSWORD")
        }
        if (!huaweiPublishingPropertiesPath.isNullOrEmpty()) {
            val properties = getPropertiesFromFile(huaweiPublishingPropertiesPath)
            HuaweiPublishing.appId = properties.getProperty("HUAWEI_PUBLISHING_APP_ID") ?: System.getenv("HUAWEI_PUBLISHING_APP_ID")
            HuaweiPublishing.clientId = properties.getProperty("HUAWEI_PUBLISHING_CLIENT_ID") ?: System.getenv("HUAWEI_PUBLISHING_CLIENT_ID")
            HuaweiPublishing.clientSecret = properties.getProperty("HUAWEI_PUBLISHING_CLIENT_SECRET") ?: System.getenv("HUAWEI_PUBLISHING_CLIENT_SECRET")
        }
        if (!onesignalPropertiesPath.isNullOrEmpty()) {
            val properties = getPropertiesFromFile(onesignalPropertiesPath)
            OneSignal.apiKey = properties.getProperty("ONESIGNAL_API_KEY") ?: System.getenv("ONESIGNAL_API_KEY")
        }
    }

    private fun getPropertiesFromFile(filePath: String): Properties {
        val propertiesFile = File(filePath)
        val properties = Properties()
        if (propertiesFile.exists()) properties.load(FileInputStream(propertiesFile))
        return properties
    }
}


fun buildCredentials(githubPropertiesPath: String? = null, keyStorePropertiesPath: String? = null, huaweiPublishingPropertiesPath: String? = null, onesignalPropertiesPath: String? = null) {
    CoreCredentials.build(githubPropertiesPath, keyStorePropertiesPath, huaweiPublishingPropertiesPath, onesignalPropertiesPath)
}