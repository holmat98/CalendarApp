plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.ANDROID_KOTLIN)
    id(Google.KSP.PLUGIN)
    id(Plugins.HILT)
    id(Plugins.JACOCO)
}

android.configure(namespace = "com.mateuszholik.data")

dependencies {

    // modules
    module(name = ":common")
    module(name = ":dateutils")

    // dependencies
    coreKtx()
    hilt()
    unitTesting()
}

tasks.withType(Test::class) {
    useJUnitPlatform()
}
