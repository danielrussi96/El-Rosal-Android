pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        maven {
            url = uri("https://maven.pkg.github.com/danielrussi96/ElRosalRepository")
            name = "com.el.rosal.business"
            credentials {
                username = "danielrussi96"
                password = "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"
            }
        }

        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    }
}

rootProject.name = "El Rosal"
include(":app")
 