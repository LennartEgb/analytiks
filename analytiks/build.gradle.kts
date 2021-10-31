plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
    testImplementation(kotlin("test-junit"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
