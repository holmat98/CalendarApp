plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.ANDROID_KOTLIN)
    id(Google.KSP.PLUGIN)
    id(Plugins.HILT)
    id(Plugins.JACOCO)
    id(AndroidGitVersion.PLUGIN)
}

androidGitVersion {
    format = AndroidGitVersion.FORMAT
    parts = AndroidGitVersion.PARTS
    multiplier = AndroidGitVersion.MULTIPLIER
}

android.configure(
    namespace = "com.mateuszholik.calendarapp",
    versionCode = androidGitVersion.code().coerceAtLeast(1),
    versionName = androidGitVersion.name() ?: "1.0.0"
)

dependencies {

    // modules
    module(name = ":common")
    module(name = ":dateutils")
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
    timber()
}

tasks.withType(Test::class) {
    useJUnitPlatform()
}
