plugins {
    `maven-publish`
    signing
    `java-library`
    groovy
}

version = "1.0"
group = "com.bloidonia"

repositories {
    jcenter()
}

dependencies {
    implementation("org.spockframework:spock-core:1.2-groovy-2.5")
    implementation("org.codehaus.groovy:groovy-all:2.5.6")
    implementation("org.codenarc:CodeNarc:1.3")

    testImplementation("org.jetbrains:annotations:13.0")
    testImplementation("org.spockframework:spock-core:1.2-groovy-2.5")
    testImplementation("junit:junit:4.12")
}

tasks.register<Jar>("sourcesJar") {
    from(sourceSets.main.get().allJava)
    archiveClassifier.set("sources")
}

tasks.register<Jar>("javadocJar") {
    from(tasks.javadoc)
    archiveClassifier.set("javadoc")
}

tasks.getByName("publish") {
    dependsOn("check")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            pom {
                from(components["java"])
                artifact(tasks["sourcesJar"])
                artifact(tasks["javadocJar"])
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
                    url.set("https://github.com/timyates/spock-ignoreuntil.git")
                }
            }
        }
    }
    repositories {
        maven {
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = findProperty("sonatypeUsername")?.toString() ?: ""
                password = findProperty("sonatypePassword")?.toString() ?: ""
            }
        }
    }

}

signing {
    sign(publishing.publications["mavenJava"])
}

