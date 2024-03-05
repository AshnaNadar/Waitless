package com.example.server.plugins

import com.example.server.models.entities.ExerciseService
import com.example.server.models.entities.GymService
import io.github.cdimascio.dotenv.Dotenv
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import com.example.server.models.entities.UserService
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    val dotenv = Dotenv.load()
    val dbUrl = dotenv["DATABASE_URL"]
    val dbUser = dotenv["DB_USER"]
    val dbPassword = dotenv["DB_PASSWORD"]

    val database = Database.connect(
        url = dbUrl,
        driver = "org.postgresql.Driver",
        user = dbUser,
        password = dbPassword
    )

    transaction {
        UserService(database = database)
        ExerciseService(database = database)
        GymService(database = database)
    }
}
