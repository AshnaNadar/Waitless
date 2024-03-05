package com.example.server
import com.example.server.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import com.example.server.routes.authRoutes
import com.example.server.routes.exerciseRoutes
import com.example.server.routes.gymRoutes
import com.example.server.routes.userRoutes
import io.github.cdimascio.dotenv.Dotenv

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

//. Retrieve ENV variable
val dotenv = Dotenv.load()
val supabaseUrl = dotenv["SUPABASE_URL"]
val supabaseAnonKey = dotenv["SUPABASE_ANON_KEY"]

fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureRouting()
    routing {
        userRoutes()
        exerciseRoutes()
        gymRoutes()
        authRoutes()
    }
}