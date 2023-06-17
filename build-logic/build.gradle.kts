/*
 * Designed and developed by "옴마야" Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/mash-up-kr/WeQuiz-Android/blob/main/LICENSE
 */

@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    `kotlin-dsl`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.gradle.dependency.handler.extensions)
}

gradlePlugin {
    val pluginClasses = listOf(
        "AndroidApplicationPlugin" to "android-application",
        "AndroidLibraryPlugin" to "android-library",
        "AndroidComposePlugin" to "android-compose",
        "JvmKotlinPlugin" to "jvm-kotlin",
        "KotlinExplicitApiPlugin" to "kotlin-explicit-api",
    )

    plugins {
        pluginClasses.forEach { pluginClass ->
            pluginRegister(pluginClass)
        }
    }
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}

sourceSets {
    getByName("main").java.srcDirs("src/main/kotlin")
}

dependencies {
    implementations(libs.gradle.android, libs.kotlin.gradle)
}

// Pair<ClassName, PluginName>
fun NamedDomainObjectContainer<PluginDeclaration>.pluginRegister(data: Pair<String, String>) {
    val (className, pluginName) = data
    register(pluginName) {
        implementationClass = className
        id = "wequiz.plugin.$pluginName"
    }
}
