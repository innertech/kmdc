import org.jetbrains.kotlin.gradle.tasks.AbstractKotlinNativeCompile
import org.jetbrains.kotlin.gradle.tasks.CInteropProcess
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.konan.target.HostManager
import util.buildHost

plugins {
  kotlin("multiplatform")
  kotlin("plugin.serialization")
  id("plugin.common")
  id("dev.petuska.klip")
}

kotlin {
  explicitApi()
  js {
    useCommonJs()
    browser()
  }
}

tasks {
  project.properties["org.gradle.project.targetCompatibility"]?.toString()?.let {
    withType<KotlinCompile> { kotlinOptions { jvmTarget = it } }
    withType<JavaCompile> { targetCompatibility = it }
  }
  withType<CInteropProcess> { onlyIf { konanTarget.buildHost == HostManager.host.family } }
  withType<AbstractKotlinNativeCompile<*, *>> {
    onlyIf { compilation.konanTarget.buildHost == HostManager.host.family }
  }
}

afterEvaluate {
  rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
    versions.webpackDevServer.version = "4.6.0"
    versions.webpack.version = "5.65.0"
    versions.webpackCli.version = "4.9.1"
  }
}
