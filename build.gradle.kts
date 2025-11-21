plugins {
    `java-library`
}

repositories {
    mavenCentral()
    maven("https://central.sonatype.com/repository/maven-snapshots/")
}

dependencies {
    api("io.github.yunan9:commons:0.1.3-SNAPSHOT")
    api("org.jetbrains:annotations:26.0.2-1")
}