plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("com.vanniktech.maven.publish")
}

kotlinMultiplatform("cafe.adriel.bonsai.json")

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.bonsaiCore)
                api(libs.serialization)
                compileOnly(compose.foundation)
                compileOnly(compose.ui)
            }
        }
    }
}
