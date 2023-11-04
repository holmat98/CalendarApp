import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Action
import org.gradle.api.JavaVersion
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

fun LibraryExtension.configure(
    namespace: String,
    isUsingCompose: Boolean = false
) {
    this.namespace = namespace
    compileSdk = DefaultConfig.COMPILE_SDK

    configureDefaultConfig()
    configureDefaultBuildTypes()
    configureJava()
    if (isUsingCompose) {
        configureBuildFeatures()
        configureComposeOptions()
    }
}

private fun LibraryExtension.configureDefaultConfig() {
    defaultConfig {
        minSdk = DefaultConfig.MIN_SDK

        vectorDrawables {
            useSupportLibrary = true
        }

        testInstrumentationRunner = DefaultConfig.TEST_INSTRUMENTATION_RUNNER
        consumerProguardFiles(DefaultConfig.CONSUMER_RULES_FILE)
    }
}

private fun LibraryExtension.configureBuildFeatures() {
    buildFeatures {
        compose = true
    }
}

private fun LibraryExtension.configureComposeOptions() {
    composeOptions {
        kotlinCompilerExtensionVersion = AndroidX.Compose.KOTLIN_COMPILER_EXTENSION_VERSION
    }
}

private fun LibraryExtension.configureDefaultBuildTypes() {
    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile(Proguard.FILE),
                Proguard.RULES
            )
        }
    }
}

private fun LibraryExtension.configureJava() {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

private fun LibraryExtension.kotlinOptions(configure: Action<KotlinJvmOptions>) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", configure)
}
