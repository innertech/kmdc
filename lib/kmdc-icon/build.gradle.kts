import de.fayard.refreshVersions.core.versionFor

plugins {
  id("plugin.library-compose")
  id("plugin.publishing-mpp")
}

val mdcIconVersion = versionFor("version.npm.material-icons")

kotlin {
  sourceSets {
    named("jsMain") {
      dependencies {
        api(project(":lib:kmdc-core"))
        api(npm("material-icons", mdcIconVersion))
      }
    }
  }
}
