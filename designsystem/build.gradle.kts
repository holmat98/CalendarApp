plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.KOTLIN)
    id(Google.KSP.PLUGIN)
    id(Plugins.HILT)
}

android.configure(
    namespace = "com.mateuszholik.designsystem",
    isUsingCompose = true
)

dependencies {
    coreKtx()
    compose()
    hilt()
}
