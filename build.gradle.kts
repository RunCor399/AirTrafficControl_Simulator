// Declaration of the Gradle extension to use
plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "5.2.0"
}
repositories {
    jcenter() // Contains the whole Maven Central
}
// List of JavaFX modules
val javaFXModules = listOf(
    "base",
    "controls",
    "fxml",
    "swing",
    "graphics"
)
// All required for OOP
val supportedPlatforms = listOf("linux", "mac", "win")

dependencies {
    //JavaFX dependency
    for (platform in supportedPlatforms) {
        for (module in javaFXModules) {
            implementation("org.openjfx:javafx-$module:13:$platform")
        }
    }
    // JUnit API and testing engine
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
    testCompile("junit:junit:4.12")
    testRuntime("org.junit.vintage:junit-vintage-engine:5.3.1")
}

tasks.withType<Test> {
    // Enables JUnit 5 Jupiter module
    useJUnitPlatform()
}

application {
    mainClassName = "view.Launcher"
}