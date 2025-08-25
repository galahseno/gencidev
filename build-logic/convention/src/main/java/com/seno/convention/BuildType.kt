package com.seno.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.util.Properties

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionType: ExtensionType
) {

    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    localProperties.load(localPropertiesFile.inputStream())

    commonExtension.run {
        buildFeatures {
            buildConfig = true
        }

        defaultConfig {
            fun prop(name: String): String =
                checkNotNull(localProperties.getProperty(name)) { "Missing $name in local.properties" }

            buildConfigField("String", "API_KEY", "\"${prop("api_key")}\"")
            buildConfigField("String", "BASE_URL", "\"${prop("base_url")}\"")
            buildConfigField("String", "BASE_IMAGE_URL", "\"${prop("base_image_url")}\"")
        }

        when (extensionType) {
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            isMinifyEnabled = false
                            proguardFiles(
                                getDefaultProguardFile("proguard-android-optimize.txt"),
                                "proguard-rules.pro"
                            )
                        }
                        release {
                            isMinifyEnabled = false
                            proguardFiles(
                                getDefaultProguardFile("proguard-android-optimize.txt"),
                                "proguard-rules.pro"
                            )
                        }
                    }
                }
            }

            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            isMinifyEnabled = false
                            proguardFiles(
                                getDefaultProguardFile("proguard-android-optimize.txt"),
                                "proguard-rules.pro"
                            )
                        }
                        release {
                            isMinifyEnabled = false
                            proguardFiles(
                                getDefaultProguardFile("proguard-android-optimize.txt"),
                                "proguard-rules.pro"
                            )
                        }
                    }
                }
            }
        }
    }
}
