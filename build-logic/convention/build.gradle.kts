plugins {
    `kotlin-dsl`
}

group = "com.seno.build-logic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApp") {
            id = "gencidev.android.application"
            implementationClass = "AndroidAppConventionPlugin"
        }
        register("androidLib") {
            id = "gencidev.android.library"
            implementationClass = "AndroidLibConventionPlugin"
        }
        register("androidLibCompose") {
            id = "gencidev.android.library.compose"
            implementationClass = "AndroidLibComposeConventionPlugin"
        }
        register("jvmLibrary") {
            id = "gencidev.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}