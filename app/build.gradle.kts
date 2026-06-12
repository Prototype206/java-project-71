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

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
    
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.0")
    
    testImplementation("org.assertj:assertj-core:3.27.7")
    
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.20.1")
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
    classDirectories.setFrom(files(classDirectories.files.map {
        fileTree(it).matching {
            exclude("hexlet/code/App.class")
        }
    }))
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

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
