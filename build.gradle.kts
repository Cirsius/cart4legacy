plugins {
    java
}

group = "net.cirsius"
version = "0"

base {
    archivesName.set("cart4legacy")
}

repositories {
    maven("https://hub.spigotmc.org/nexus/content/groups/public/")
    mavenCentral()
}

dependencies {
    compileOnly("org.bukkit:bukkit:1.14-R0.1-SNAPSHOT")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-g:none")
}
