plugins {
    id("java")
    id("com.gradleup.shadow") version "9.0.0"
}

group = "dev.lokspel.totempractice"
version = "1.0.1"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-releases/")
    maven("https://repo.helpch.at/releases/")
    maven("https://repo.faststats.dev/releases")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.3.build.+")
    implementation("dev.faststats.metrics:bukkit:0.30.2")
    compileOnly("com.github.retrooper:packetevents-spigot:2.14.0")
    compileOnly("me.clip:placeholderapi:2.12.3")
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(25)
    options.encoding = "UTF-8"
}

tasks.shadowJar {
    exclude("META-INF/versions/*/module-info.class", "module-info.class")
    archiveClassifier.set("")
}

tasks.jar {
    enabled = false
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.processResources {
    filesMatching("plugin.yml") {
        expand(mapOf("version" to project.version))
    }
}
