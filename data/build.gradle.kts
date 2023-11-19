plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.KOTLIN)
    id(Google.KSP.PLUGIN)
    id(Plugins.HILT)
}

android.configure(namespace = "com.mateuszholik.data")

dependencies {

    coreKtx()
    hilt()
    unitTesting()
}

tasks.withType(Test::class) {
    useJUnitPlatform()
}
