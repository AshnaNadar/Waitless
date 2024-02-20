# Team-14

### Get Started

### Using the ENV vars:

Example:

`val dotenv = dotenv {
    directory = "/assets"
    filename = "env"
}

val DB_URL = dotenv["DATABASE_URL"]`

### Design Pattern

**MVVM Pattern**
MVVM offers two-way binding between view and view-model. It makes use of the  observer pattern to make changes in view model
- Model: stores data + related logic (interacts with DB)
- View: standard UI components + pages
- View-Model: functions, commands, methods to support state of Views


### Folder Structure
root_project_directory/
│
├── app/                              # Android application module
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── example/
│   │   │   │           └── waitless/
│   │   │   │               ├── ui/            # UI package for Activities, Fragments, and Composables
│   │   │   │               │   ├── components/ # Reusable Composable components
│   │   │   │               │   ├── pages/      # Composable screens (e.g., LoginPage.kt, HomePage.kt)
│   │   │   │               │   └── viewmodels/ # ViewModel classes
│   │   │   │               └── model/         # Model classes (entities and use cases)
│   │   │   └── res/         # Android resources (layouts, values, etc.)
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts      # Gradle build script for the Android module
│
└── server/                           # Server module with Ktor
├── src/
│   ├── main/
│   │   ├── kotlin/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── waitless/
│   │   │               ├── Application.kt # Ktor server application entry point
│   │   │               ├── data/          # Data handling (Repositories, Database config)
│   │   │               │   ├── repository/ # Data access layer
│   │   │               │   └── DatabaseFactory.kt # Database connection and configuration
│   │   │               ├── model/         # Model classes (entities and DTOs)
│   │   │               └── routes/        # Ktor route definitions
│   │   └── resources/
│   │       └── db/
│   │           └── migration/             # Database migration scripts for Flyway
│   └── build.gradle.kts  # Gradle build script for the server module


### Links
[Wiki](../../wikis/Project-Proposal)