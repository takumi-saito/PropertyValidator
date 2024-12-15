import java.util.Properties

plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id("java-library") // ライブラリプラグイン
    id("maven-publish") // Maven公開用
}

dependencies {
    implementation(libs.symbol.processing.api)
}

group = "t.saito.com.lib.property_validator" // パッケージ名
version = "1.0.0" // バージョン番号

java {
    withSourcesJar() // ソースコードのJarを含める
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17)) // Javaのバージョンを指定
    }
}

// local.properties の読み込み
val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        load(file.inputStream())
    }
}

publishing {
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
            groupId = "t.saito.com.lib" // パッケージ名
            artifactId = "property-validator" // ライブラリ名
            version = "1.0.0"
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/takumi-saito/property-validator")

            credentials {
                username = localProperties.getProperty("gpr.user")
                password = localProperties.getProperty("gpr.token")
            }
        }
    }
}