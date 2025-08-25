plugins {
    alias(libs.plugins.gencidev.android.library.compose)
}
android {
    namespace = "com.seno.home.presentation"
}

dependencies {
    implementation(libs.androidx.paging.compose)
    implementation(libs.coil.compose)

    with(projects) {
        implementation(core.presentation)
        implementation(core.domain)
        implementation(home.domain)
    }
}