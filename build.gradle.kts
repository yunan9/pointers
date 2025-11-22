plugins {
    alias(libs.plugins.jmh)
    alias(libs.plugins.indra.publishing)

    `java-library`
    `maven-publish`
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }

    withSourcesJar()
    withJavadocJar()
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

indra {
    javaVersions {
        target(21)
    }

    github("yunan9", "pointers") {
        ci(true)
    }

    mitLicense()

    configurePublications {
        from(components["java"])

        pom {
            developers {
                developer {
                    id = "yunan9"
                    name = "Yunan"
                }
            }
        }
    }
}