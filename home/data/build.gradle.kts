plugins {
    alias(libs.plugins.gencidev.android.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.seno.home.data"
}

dependencies {
    implementation(libs.bundles.ktor)
    implementation(libs.room.paging)

    with(projects) {
        implementation(core.domain)
        implementation(core.data)
        implementation(home.domain)
    }
}