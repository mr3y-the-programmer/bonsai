plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("com.vanniktech.maven.publish")
}

kotlinMultiplatform("cafe.adriel.bonsai.filesystem")

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.bonsaiCore)
                api(libs.okio)
                compileOnly(compose.foundation)
                compileOnly(compose.ui)
                compileOnly(compose.materialIconsExtended)
            }
        }
    }
}
