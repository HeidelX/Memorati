pluginManagement {
    includeBuild("build-logic")
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
    }
}

rootProject.name = "Memorati"
include(":app")
include(":core:db")
include(":core:data")
include(":core:ui")
include(":core:model")
include(":feature:cards")
include(":feature:favourites")
include(":feature:assistant")
include(":core:design")
include(":feature:creation")
