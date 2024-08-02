

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.popdate"
    compileSdk = 34

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.popdate"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    // Packaging options to exclude duplicate files
    packagingOptions {
        resources {
            excludes += setOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE",
                "META-INF/NOTICE.md",
                "META-INF/NOTICE"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    //noinspection UseTomlInstead
    implementation(kotlin("stdlib"))
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(libs.androidx.constraintlayout)
    //noinspection UseTomlInstead
    implementation ("com.github.bumptech.glide:glide:4.14.2")
    //noinspection GradleDependency
    implementation("com.kakao.sdk:v2-user:2.20.4")
    implementation(libs.androidx.viewbinding)
    implementation(libs.androidx.databinding.compiler.common)
    implementation(libs.androidx.navigation.runtime.ktx) // 카카오 로그인

    implementation("androidx.navigation:navigation-runtime-ktx:2.7.7")
    implementation ("com.google.android.material:material:1.12.0")

    implementation("androidx.room:room-runtime:2.6.1")
    testImplementation(libs.junit.junit)
    kapt("androidx.room:room-compiler:2.6.1") // Room 컴파일러 추가
    implementation("androidx.room:room-ktx:2.6.1")

    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")

    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}
