
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.7.5" apply false
}

buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48.1") // Auf neueste Version aktualisiert
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.5") // Auf neueste Version aktualisiert
    }
}