import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Action
import org.gradle.api.JavaVersion
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

fun BaseAppModuleExtension.configure(
    namespace: String,
    versionCode: Int,
    versionName: String,
) {
    this.namespace = namespace
    compileSdk = DefaultConfig.COMPILE_SDK

    configureDefaultConfig(
        namespace = namespace,
        versionCode = versionCode,
        versionName = versionName
    )
    configureBuildFeatures()
    configureDefaultBuildTypes()
    configureJava()
    configureComposeOptions()
    configurePackaging()
}

private fun BaseAppModuleExtension.configureDefaultConfig(
    namespace: String,
    versionCode: Int,
    versionName: String,
) {
    defaultConfig {
        applicationId = namespace
        minSdk = DefaultConfig.MIN_SDK
        targetSdk = DefaultConfig.TARGET_SDK
        this.versionCode = versionCode
        this.versionName = versionName

        vectorDrawables {
            useSupportLibrary = true
        }

        testInstrumentationRunner = DefaultConfig.TEST_INSTRUMENTATION_RUNNER
    }
}

private fun BaseAppModuleExtension.configureBuildFeatures() {
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

private fun BaseAppModuleExtension.configureComposeOptions() {
    composeOptions {
        kotlinCompilerExtensionVersion = AndroidX.Compose.KOTLIN_COMPILER_EXTENSION_VERSION
    }
}

private fun BaseAppModuleExtension.configurePackaging() {
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

private fun BaseAppModuleExtension.configureDefaultBuildTypes() {
    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile(Proguard.FILE),
                Proguard.RULES
            )
        }
    }
}

private fun BaseAppModuleExtension.configureJava() {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

private fun BaseAppModuleExtension.kotlinOptions(configure: Action<KotlinJvmOptions>) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", configure)
}
