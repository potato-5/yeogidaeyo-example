plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.secrets.gradle.plugin)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.hyun.sesac"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.hyun.sesac"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            // abiFilters.add("armeabi-v7a") // 이건 지우거나 주석 유지
            abiFilters.add("arm64-v8a")      // ⭐ 이거 하나만 활성화!
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

secrets{
    propertiesFileName = "secret.properties"
}

kotlin{ jvmToolchain(21) }

dependencies {
    implementation(project(":features:auth"))
    implementation(project(":features:home"))
    implementation(project(":features:mypage"))
    implementation(project(":features:register"))
    implementation(project(":shared"))
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.libraries)
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.retrofits)
    implementation(libs.bundles.okhttps)
    ksp(libs.moshi.codegen)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.compose.material.icons.extended)

    implementation(libs.maps.compose)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore.ktx)
    /**
     * <meta-data
     *   android:name="com.kakao.sdk.AppKey"
     *   android:value="{KAKAO_NATIVE_APP_KEY}" />
     *   사용하기 위함
     */
    implementation(libs.v2.user)
    implementation(libs.kakao.maps)
    implementation(libs.v2.all)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
