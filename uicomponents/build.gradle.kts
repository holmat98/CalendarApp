plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.ANDROID_KOTLIN)
}

android.configure(
    namespace = "com.mateuszholik.uicomponents",
    isUsingCompose = true
)

dependencies {

    // Modules
    module(name = ":designsystem")

    // Dependencies
    coreKtx()
    compose()
    lottie()
}
