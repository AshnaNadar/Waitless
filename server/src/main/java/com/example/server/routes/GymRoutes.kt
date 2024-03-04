package com.example.server.routes

import com.example.server.data.repository.UserRepository
import com.example.server.models.entities.ExposedUser
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.server.plugins.SupabaseClient
import com.example.server.plugins.SupabaseClient.dotenv
import com.example.server.plugins.UserSession
import io.github.jan.supabase.gotrue.auth
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions

fun Route.gymRoutes() {
    val supabase = SupabaseClient.supabase
    val supabaseServiceRoleKey = dotenv["SUPABASE_SERVICE_ROLE_KEY"] ?: error("SUPABASE_SERVICE_ROLE_KEY not set")

    // Read Gym Details
    get("/gyms/{id}") {
        val userSession = call.sessions.get<UserSession>()
        if (userSession != null) {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID")
            call.respondText("Accessing protected data with session: ${userSession.accessToken}")
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Please login to access this resource")
        }
    }

}
