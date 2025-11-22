plugins {
    id("me.champeau.jmh") version "0.7.3"
    `java-library`
}

repositories {
    mavenCentral()
    maven("https://central.sonatype.com/repository/maven-snapshots/")
}

dependencies {
    api("io.github.yunan9:commons:0.1.3-SNAPSHOT")
    api("org.jetbrains:annotations:26.0.2-1")

    jmh(parseJmhDependencyAnnotation("jmh-core"))
    jmhAnnotationProcessor(parseJmhDependencyAnnotation("jmh-generator-annprocess"))
}

fun parseJmhDependencyAnnotation(artifactId: String) = "org.openjdk.jmh:$artifactId:1.37"