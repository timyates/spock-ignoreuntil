plugins {
    `maven-publish`
    `java-library`
    groovy
}

version = "0.1"
group = "com.bloidonia"

repositories {
    jcenter()
}

dependencies {
    implementation("org.spockframework:spock-core:1.2-groovy-2.5")
    implementation("org.codehaus.groovy:groovy-all:2.5.6")

    testImplementation("org.jetbrains:annotations:13.0")
    testImplementation("org.spockframework:spock-core:1.2-groovy-2.5")
    testImplementation("junit:junit:4.12")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            pom {
                name.set("IgnoreUntil Spock Extension")
                description.set("Allows an end date within 30 days to be specified for an ignored spec or feature")
                url.set("https://github.com/timyates/spock-ignoreuntil")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("timyates")
                        name.set("Tim Yates")
                        email.set("tim@bloidonia.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/timyates/spock-ignoreuntil.git")
                }
            }
        }
    }
}