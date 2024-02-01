plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.ANDROID_KOTLIN)
    id(Google.KSP.PLUGIN)
    id(Plugins.HILT)
}

android.configure(namespace = "com.mateuszholik.domain")

dependencies {

    // Modules
    module(name = ":data")

    // Dependencies
    coreKtx()
    hilt()
}
