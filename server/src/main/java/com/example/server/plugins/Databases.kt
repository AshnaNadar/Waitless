package com.example.server.plugins

import io.github.cdimascio.dotenv.Dotenv
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabases() {
    val dotenv = Dotenv.load()
    val dbUrl = dotenv["DATABASE_URL"]
    val dbUser = dotenv["DB_USER"]
    val dbPassword = dotenv["DB_PASSWORD"]

    Database.connect(
        url = dbUrl,
        driver = "org.postgresql.Driver",
        user = dbUser,
        password = dbPassword
    )
}
