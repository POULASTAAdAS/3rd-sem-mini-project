plugins {
    id("java")
}

group = "com.poulastaa.service"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    // jdbc
    implementation("mysql:mysql-connector-java:8.0.32")
    implementation("org.hibernate.orm:hibernate-core:7.1.0.Final")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}