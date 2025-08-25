plugins {
    alias(libs.plugins.gencidev.jvm.library)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.androidx.paging.common)
    implementation(projects.core.domain)
}
