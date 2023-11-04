plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.KOTLIN)
}

android.configure(
    namespace = "com.mateuszholik.designsystem",
    isUsingCompose = true
)

dependencies {
    coreKtx()
    compose()
}
