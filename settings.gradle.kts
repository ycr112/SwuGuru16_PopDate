import com.android.build.api.variant.Packaging

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

plugins {
    id("com.android.application") version "8.5.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}

    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            google()
            mavenCentral()
            //maven{ url 'https://devrepo.kakao.com/nexus/content/groups/public/' }
            maven { url = uri("https://www.jitpack.io" ) }
            // 카카오 로그인
            maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/" ) }
        }
    }

include(":app")
