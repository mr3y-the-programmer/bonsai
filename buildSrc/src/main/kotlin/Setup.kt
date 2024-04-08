import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.android.build.gradle.LibraryExtension
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

private fun BaseExtension.android(namespace: String) {
    this.namespace = namespace
    compileSdkVersion(34)
    defaultConfig {
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
fun Project.kotlinMultiplatform(
    namespace: String,
    explicitMode: Boolean = true
) {
    extensions.configure<KotlinMultiplatformExtension> {
        androidTarget {
            publishAllLibraryVariants()
        }
        jvm("desktop")

        /* Source sets structure
            common
              ├─ jvm
                  ├─ android
                  ├─ desktop
         */
        applyHierarchyTemplate {
            common {
                group("jvm") {
                    withAndroidTarget()
                    withJvm()
                }
            }
        }
    }

    extensions.configure<LibraryExtension> {
        android(namespace)
        sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.setup(explicitMode)
    }

}

private fun KotlinJvmOptions.setup(
    enableExplicitMode: Boolean
) {
    jvmTarget = JavaVersion.VERSION_1_8.toString()

    if (enableExplicitMode) freeCompilerArgs += "-Xexplicit-api=strict"
}
