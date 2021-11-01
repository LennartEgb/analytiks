plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
    testImplementation(kotlin("test-junit"))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
