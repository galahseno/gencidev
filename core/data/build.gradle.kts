plugins {
    alias(libs.plugins.gencidev.android.library)
    alias(libs.plugins.ksp)
}
android {
    namespace = "com.seno.core.data"
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.bundles.ktor)

    implementation(libs.timber)
    implementation(libs.bundles.room)

    ksp(libs.room.compiler)
}