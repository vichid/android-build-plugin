package io.github.vichid

fun missing(property: String): Nothing = throw IllegalStateException(
    "Add missing $property property to your gradle.properties file"
)
