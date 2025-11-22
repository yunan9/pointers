plugins {
    alias(libs.plugins.jmh.gradle.plugin)

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
    compileOnly(libs.yunan9.commons)
    compileOnly(libs.jetbrains.annotations)

    jmh(libs.jmh.core)
    jmhAnnotationProcessor(libs.jmh.generator.annprocess)
}

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