pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/takumi-saito/property-validator")
            val props = loadProperties(file("$rootDir/github.properties"))
            credentials {
                username = props["gpr.user"] as String
                password = props["gpr.token"] as String
            }
        }
    }
}

fun loadProperties(propertiesFile: File) = java.util.Properties().apply {
    propertiesFile.inputStream().use { fis ->
        load(fis)
    }
}

rootProject.name = "PropertyValidatorUsageExample"
include(":app")
// module dependency
//include(":property_validator")
