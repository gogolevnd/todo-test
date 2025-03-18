plugins {
    id("java")
    id("io.spring.dependency-management") version "1.1.7"
    id ("io.qameta.allure") version "2.12.0"
}

group = "ru.some.test.todo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

val springVersion:String by extra ("3.4.3")
val allureVersion:String by extra ("2.30.0")

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:$springVersion")
    }
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.projectlombok:lombok")
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("io.rest-assured:rest-assured")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    annotationProcessor("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")
}

allure {
    version.set(allureVersion)
}

tasks.test {
    val springProfile :String by project
    systemProperty("spring.profiles.active", springProfile)
    useJUnitPlatform()
}