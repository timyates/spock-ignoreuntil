plugins {
    `java-library`
    groovy
    codenarc
}

repositories {
    jcenter()
}

dependencies {
    codenarc("com.bloidonia:spock-ignoreuntil:1.0")
    api("org.apache.commons:commons-math3:3.6.1")
    implementation("com.google.guava:guava:27.0.1-jre")
    testImplementation("org.codehaus.groovy:groovy-all:2.5.6")
    testImplementation("org.spockframework:spock-core:1.2-groovy-2.5")
    testImplementation("com.bloidonia:spock-ignoreuntil:1.0")
    testImplementation("junit:junit:4.12")
}

tasks.named("codenarcTest", CodeNarc::class.java) {
    configFile = file("config/codenarc/codenarc.rules")
}