plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN)
    id(Google.KSP.PLUGIN)
    id(Plugins.HILT)
}

android.configure(
    namespace = "com.mateuszholik.calendarapp",
    versionCode = 1,
    versionName = "1.0"
)

dependencies {

    // modules
    module(name = ":designsystem")
    module(name = ":domain")
    module(name = ":uicomponents")

    // dependencies
    coreKtx()
    lifecycle()
    activity()
    compose()
    hilt()
    unitTesting()
}

tasks.withType(Test::class) {
    useJUnitPlatform()
}
