plugins {
    application
    id("com.github.ben-manes.versions") version "0.54.0"
    java
    checkstyle
    jacoco
    id("org.sonarqube") version "7.1.0.6387"
}

group = "hexlet.code"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("info.picocli:picocli:4.7.7")
    annotationProcessor("info.picocli:picocli-codegen:4.7.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.20.1")

    testImplementation("org.junit.jupiter:junit-jupiter:6.1.0")
    testImplementation("org.assertj:assertj-core:3.27.7")
}

jacoco {
    toolVersion = "0.8.11"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

application {
    mainClass.set("hexlet.code.App")
}

sonar {
    properties {
        property("sonar.projectKey", "Prototype206_java-project-71")
        property("sonar.organization", "prototypes-organization")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "$buildDir/reports/jacoco/test/jacocoTestReport.xml")
    }
}



checkstyle {
    toolVersion = "12.1.2"
    configFile = file("${projectDir}/config/checkstyle/checkstyle.xml")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
    systemProperty("junit.jupiter.testinstance.lifecycle.default", "per_class")
}

