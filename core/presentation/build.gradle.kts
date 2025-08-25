plugins {
    alias(libs.plugins.gencidev.android.library.compose)
}

android {
    namespace = "com.seno.core.presentation"

}

dependencies {
    implementation(projects.core.domain)
}