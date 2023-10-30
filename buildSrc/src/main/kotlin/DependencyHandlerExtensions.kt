import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.coreKtx() {
    implementation(AndroidX.CoreKtx.DEPENDENCY)
}

fun DependencyHandler.lifecycle() {
    implementation(AndroidX.Lifecycle.DEPENDENCY)
}

fun DependencyHandler.activity() {
    implementation(AndroidX.ActivityCompose.DEPENDENCY)
}

fun DependencyHandler.hilt() {
    implementation(Google.Hilt.DEPENDENCY)
    ksp(Google.Hilt.Compiler.DEPENDENCY)
}

fun DependencyHandler.compose() {
    implementation(AndroidX.Compose.UI)
    implementation(AndroidX.Compose.MATERIAL)
    implementation(AndroidX.Compose.PREVIEW)
    implementation(AndroidX.Compose.NAVIGATION)
    debugImplementation(AndroidX.Compose.UI_TOOLING)
    debugImplementation(AndroidX.Compose.TEST_MANIFEST)
    implementation(AndroidX.Compose.Hilt.DEPENDENCY)
}

private fun DependencyHandler.implementation(dependency: String) {
    add("implementation", dependency)
}

private fun DependencyHandler.debugImplementation(dependency: String) {
    add("debugImplementation", dependency)
}

private fun DependencyHandler.ksp(dependency: String) {
    add("ksp", dependency)
}
