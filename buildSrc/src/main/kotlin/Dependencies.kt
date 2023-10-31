object Plugins {
    const val ANDROID_APPLICATION = "com.android.application"
    const val KOTLIN = "org.jetbrains.kotlin.android"
    const val HILT = "com.google.dagger.hilt.android"
}

object DefaultConfig {
    const val COMPILE_SDK = 34
    const val MIN_SDK = 30
    const val TARGET_SDK = 34
    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
}

object GradlePlugins {
    const val KOTLIN_VERSION = "1.9.10"
    const val GRADLE_PLUGIN_VERSION = "8.1.2"
}

object Proguard {
    const val FILE = "proguard-android-optimize.txt"
    const val RULES = "proguard-rules.pro"
}

object AndroidX {

    object CoreKtx {
        private const val version = "1.12.0"

        const val DEPENDENCY = "androidx.core:core-ktx:$version"
    }

    object Lifecycle {
        private const val version = "2.6.2"

        const val DEPENDENCY = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
    }

    object ActivityCompose {
        private const val version = "1.8.0"

        const val DEPENDENCY = "androidx.activity:activity-compose:$version"
    }

    object Compose {
        private const val composeVersion = "1.5.2"

        const val KOTLIN_COMPILER_EXTENSION_VERSION = "1.5.3"
        const val UI = "androidx.compose.ui:ui:$composeVersion"
        const val MATERIAL = "androidx.compose.material3:material3:1.2.0-alpha02"
        const val PREVIEW = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
        const val UI_TOOLING = "androidx.compose.ui:ui-tooling:$composeVersion"
        const val NAVIGATION = "androidx.navigation:navigation-compose:2.7.3"
        const val TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest:$composeVersion"

        object Hilt {
            private const val version = "1.0.0"

            const val DEPENDENCY = "androidx.hilt:hilt-navigation-compose:$version"
        }
    }
}

object Google {

    object KSP {
        const val version = "1.9.10-1.0.13"

        const val PLUGIN = "com.google.devtools.ksp"
    }

    object Hilt {
        const val version = "2.48"

        const val PLUGIN = "com.google.dagger.hilt.android"
        const val DEPENDENCY = "com.google.dagger:hilt-android:$version"

        object Compiler {
            const val DEPENDENCY = "com.google.dagger:hilt-android-compiler:$version"
        }
    }
}
