package com.example.server.routes

import com.example.server.data.repository.GymAdminRepository
import com.example.server.data.repository.GymRepository
import com.example.server.models.entities.ExposedGym
import com.example.server.models.entities.ExposedGymAdmin
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.gymRoutes() {
    val gymRepository = GymRepository()
    val gymAdminRepository = GymAdminRepository()

    // Create gym

    post("/gym/{id}/create") {
        val req = call.receive<ExposedGym>()
        try {
            val gym = gymRepository.createGym(req)
            call.respond(HttpStatusCode.Created, "Gym created successfully: ${gym.value}")
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Failed to create gym: ${e.localizedMessage}")
        }

    }
    // Delete gym

    delete("/gym/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID")
        gymRepository.deleteGym(id)
        call.respond(HttpStatusCode.OK)
    }

    // Create gym admin

    post("/gym/{gym_id}/admin/create") {
        val req = call.receive<ExposedGymAdmin>()
        try {
            val gymAdmin = gymAdminRepository.createGymAdmin(req)
            call.respond(HttpStatusCode.Created, "Gym admin created successfully: ${gymAdmin.value}")
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Failed to create gym admin: ${e.localizedMessage}")
        }
    }

    // Delete gym admin

    delete("/gym/{gym_id}/admin/{user_id}/delete") {
        val gym_id = call.parameters["gym_id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid Gym ID")
        val user_id = call.parameters["user_id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid User ID")
        gymAdminRepository.deleteGymAdmin(gym_id, user_id)
        call.respond(HttpStatusCode.OK)
    }
}
