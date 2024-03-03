plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Xlint:unchecked")
}
dependencies {
    implementation("com.fasterxml.jackson.core:jackson-core:2.13.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
    implementation("org.apache.httpcomponents:httpclient:4.5.14")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.1")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    // JUnit 5 для тестирования
    testImplementation("org.mockito:mockito-core:3.12.4")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("junit:junit:4.13.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.mockito:mockito-core:3.12.4")
    // Mockito для создания заглушек
    testImplementation("org.mockito:mockito-core:3.12.4")
}


tasks.test {

    useJUnitPlatform()
}