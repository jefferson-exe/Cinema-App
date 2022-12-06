# Cinema-App

### 1. More models (lab 9)
In lab 9 I have create a skeleton cinema app for the project I have created two models and API classes:
### Model
- Cinema
- Movie
### API
- CinemaAPI
- MovieAPI

### Features
During the creation process the features that I have added to the app includes:
### Cinema
- addCinema
- updateCinema
- archiveCinema
- deleteCinema
- listCinema (with sub menu - listAllCinemas, listCurrentCinema, listArchivedCinemas)

### Movie
- addMovie
- updateMovie
- archiveMovie
- deleteMovie
- listMovie (with sub menu - listAllMovies, listCurrentMovie, listArchivedMovie)

### 2. Gradle task (lab 10)
#### Adding Dokka
In this part of the app creation I have added documentation tool called Dokka via Gradle
and added the code in my build.gradle.kts: 

plugins {

    // kotlin("jvm") version "1.7.10"
    
    // Plugin for Dokka - KDoc generating tool
    id("org.jetbrains.dokka") version "1.6.10"
    application
}

I have also included the dependencies to generate a Dokka Site with this code and sync it to gradle:

dependencies {

    // testImplementation(kotlin("test"))
    
    // For generating a Dokka Site from KDoc
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.6.10")
    
    
}

#### Adding Jacoco
I have added Jacoco for generating a website for code coverage:

plugins {

    // kotlin("jvm") version "1.7.10"
    // Plugin for Dokka - KDoc generating tool
    // id("org.jetbrains.dokka") version "1.6.10"
    // jacoco
    // application
}

In order to run the new tasks Jacoco I added the following code in the build.gradle.kts file:

tasks.test {

    // useJUnitPlatform()
    // report is always generated after tests run
    // finalizedBy(tasks.jacocoTestReport)
}

### 3. Gradle task (lab 10)
In this part of the app creation I had to use ***GitHub Actions*** to create a defualt Github workflow
in my remote repository.


