plugins {
  id("plugin.publishing-nexus")
  id("org.jetbrains.dokka")
}

gradleEnterprise {
  buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
  }
}
