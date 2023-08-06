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
include(":core:domain")
include(":core:common")
include(":core:design")
include(":core:testing")
include(":core:datastore")
include(":feature:cards")
include(":feature:favourites")
include(":feature:assistant")
include(":feature:creation")
include(":feature:settings")
include(":feature:quiz")
