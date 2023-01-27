
plugins {
    kotlin("jvm") version "1.7.21"
    application
}

group = "io.github.idknicks.warp"
version = "1.0.1"

repositories {
    mavenCentral()
    maven {
        url = uri("http://feather-s.kr:8083/repository/maven-public/")
        isAllowInsecureProtocol = true
    }
}

dependencies {

    /** CORE */
    compileOnly("net.starly.core:ST-Core:1.5.5")

    /** BUKKIT API */
    compileOnly("org.spigotmc:spigot-api:R0.1:1.16.5")

    /** OTHERS */
    implementation("org.jetbrains:annotations:23.0.0")
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
}


/** BUILD DESTINATION DIRECTORY */
buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    }
}

apply(plugin = "kotlin")
apply(plugin = "com.github.johnrengelman.shadow")

tasks.withType<Jar> {
    archiveFileName.set("${rootProject.name}.jar")
}

val projectMainClass = "AppKt"
tasks.withType<Jar> {
    manifest {
        attributes(mapOf(
            "Main-Class" to projectMainClass
        ))
    }
}

application {
    mainClass.set(projectMainClass)
}


/** BUILD SECTION */
tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    dependsOn(tasks.processResources)
    archiveFileName.set("${rootProject.name}.jar")

    doLast {
        copy {
            val sep = File.separator
            from("${buildDir.absolutePath}${sep}libs$sep${project.name}.jar")
            into("C:\\Users\\Yangd\\대영\\마인크래프트\\서버\\마인크래프트\\개발용\\스탈리\\plugins")
        }
    }
}