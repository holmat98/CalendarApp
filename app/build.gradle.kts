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

    coreKtx()
    lifecycle()
    activity()
    compose()
    hilt()
}
