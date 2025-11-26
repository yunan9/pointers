plugins {
    alias(libs.plugins.jmh)
    alias(libs.plugins.indra.publishing)
    alias(libs.plugins.indra.publishing.sonatype)

    `java-library`
}

val javaVersion = 21

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(javaVersion)
    }

    withSourcesJar()
    withJavadocJar()
}

configurations {
    jmhImplementation.configure {
        extendsFrom(compileOnly.get())
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

tasks {
    withType<Javadoc> {
        configureEach {
            (options as StandardJavadocDocletOptions).apply {
                addBooleanOption("html5", true)

                encoding = "UTF-8"
                charSet = "UTF-8"
            }
        }
    }
}

indra {
    javaVersions {
        target(javaVersion)
    }

    github("yunan9", "pointers") {
        ci(true)
    }

    mitLicense()

    signWithKeyFromPrefixedProperties("yunan9")

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