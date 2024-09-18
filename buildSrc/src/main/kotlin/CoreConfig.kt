import org.gradle.api.Project
import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties

object CoreConfig {
    private val String.md5: String
        get() {
            val md5: MessageDigest?
            try {
                md5 = MessageDigest.getInstance("MD5")
            } catch (e: Exception) {
                e.printStackTrace()
                return ""
            }

            val charArray = this.toCharArray()
            val byteArray = ByteArray(charArray.size)
            for (i in charArray.indices) {
                byteArray[i] = charArray[i].toByte()
            }
            val md5Bytes = md5!!.digest(byteArray)
            val hexValue = StringBuffer()
            for (i in md5Bytes.indices) {
                val `val` = md5Bytes[i].toInt() and 0xff
                if (`val` < 16) {
                    hexValue.append("0")
                }
                hexValue.append(Integer.toHexString(`val`))
            }
            return hexValue.toString().substring(8, 24)
        }

    private val simpleDateFormat = SimpleDateFormat("yyMMdd")
    val kotlinVersion: String = getProperties()["kotlinVersion".md5] as String
    val buildGradleVersion: String = getProperties()["buildGradleVersion".md5] as String
    val isCIMachine: Boolean = System.getenv().containsKey("APPCENTER_BUILD_ID")

    object Android {
        val minSdk: Int = (getProperties()["androidMinSdk".md5] as String).toInt()
        val targetSdk: Int = (getProperties()["androidTargetSdk".md5] as String).toInt()
        val compileSdk: Int = (getProperties()["androidCompileSdk".md5] as String).toInt()
        val buildTools: String = getProperties()["androidBuildTools".md5] as String
    }

    object App {
        const val majorVersion: Int = 1
        const val minorVersion: Int = 0
        const val hotfixVersion: Int = 0
        const val versionCode: Int = 2
        val versionName: String = "$majorVersion.$minorVersion.$hotfixVersion.${simpleDateFormat.format(Date())}"
    }

    private var properties: Properties? = null
    private fun getProperties(): Properties {
        if (properties == null) {
            val propertiesFile = File("${System.getProperty("user.dir")}/version.properties")
            val properties = Properties()
            if (propertiesFile.exists()) properties.load(FileInputStream(propertiesFile))
            this.properties = properties
        }
        return properties!!
    }

    private fun logEnvironment() {
        // show environment for logging in ci machine
        if (isCIMachine) System.getenv().forEach { (key, value) -> println("env $key:$value") }
        if (isCIMachine) System.getProperties().forEach { (key, value) -> println("env $key:$value") }
    }

    fun coreDependencyOf(groupArtifact: String): String {
        return getProperties()[groupArtifact.md5] as? String ?: throw Exception("Task : coreDependencyOf() can not find library version : $groupArtifact")
    }

    fun coreVersionOf(groupArtifact: String): String = coreDependencyOf(groupArtifact).split(":").last()

    fun build(project: Project) {
        logEnvironment()
    }

}

fun Project.coreDependencyOf(groupArtifact: String): String = CoreConfig.coreDependencyOf(groupArtifact)
fun Project.coreVersionOf(groupArtifact: String): String = CoreConfig.coreVersionOf(groupArtifact)
fun Project.buildCoreConfig() = CoreConfig.build(this)
fun Project.buildFile(path: String, content: String) {
    val versionFile = File(path)
    if (versionFile.exists()) versionFile.delete()
    versionFile.printWriter().use { it.print(content) }
}
