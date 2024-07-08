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
include(":testSharedApp")
