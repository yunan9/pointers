plugins {
    id("me.champeau.jmh") version "0.7.3"

    `java-library`
    `maven-publish`
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
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

//TODO -> Either rework this to work with releases, or move to KyoriPowered's Indra for Maven publications.
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            val githubRepository = System.getenv("GITHUB_REPOSITORY")
            val githubRepositoryUrl = "https://github.com/$githubRepository"
            pom {
                url = githubRepositoryUrl
                name = project.name
                description = project.description

                developers {
                    developer {
                        id = "yunan9"
                    }
                }

                scm {
                    url = githubRepositoryUrl
                    connection = "scm:git:$githubRepositoryUrl.git"
                    developerConnection = "scm:git:ssh://git@github.com/$githubRepository.git"
                }
            }
        }
    }

    repositories {
        maven {
            url = uri("https://central.sonatype.com/repository/maven-snapshots/")

            credentials {
                username = System.getenv("OSSRH_USERNAME")
                password = System.getenv("OSSRH_TOKEN")
            }
        }
    }
}