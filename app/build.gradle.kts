plugins {
    alias(libs.plugins.gencidev.android.application)
}

android {
    namespace = "com.seno.gencidev"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material.icons.extended)

    implementation(libs.androidx.core.splashscreen)
    implementation(libs.kotlinx.serialization)
    implementation(libs.timber)
    implementation(libs.bundles.koin.compose)

    with(projects) {
        implementation(core.data)
        implementation(core.domain)
        implementation(core.presentation)

        implementation(home.data)
        implementation(home.domain)
        implementation(home.presentation)
    }
}