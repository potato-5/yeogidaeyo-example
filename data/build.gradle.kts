plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.secrets.gradle.plugin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.hyun.sesac.data"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
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
}

secrets {
    propertiesFileName = "secret.properties"
}

kotlin { jvmToolchain(21) }

dependencies {
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.play.services.location)

    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.retrofits)
    implementation(libs.bundles.okhttps)
    ksp(libs.moshi.codegen)

    // Room DB
        implementation(libs.bundles.room.libraries)
        ksp(libs.androidx.room.compiler){
            exclude(group = "com.intellij", module = "annotations")
        }

    // Hilt Core
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // DataStore Preference
    implementation(libs.androidx.datastore.preferences)

    //Firestore
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore.ktx)
}

//Hilt Annotation 붙일 곳이 헷갈리면 알아서 파라미터 쪽으로 잘 연결하라고 컴파일에 요구
//생성자 속성에 Annotation 을 붙이면 Byte Code 변환시 컴파일러는 헷갈림
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        freeCompilerArgs.add("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
    }
}