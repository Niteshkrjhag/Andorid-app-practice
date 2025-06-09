pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "RecipeApp"
include(":app")
include(":common")
include(":media_player")
include(":feature:search:ui")
include(":feature:search:domain")
include(":feature:search:data")

// to get feature->search->[data,domain,ui module] project->directory->
// feature->search->new module ui then change move to search and in settings.gradle.kts use
// :featue:search:ui then everything will be solve
