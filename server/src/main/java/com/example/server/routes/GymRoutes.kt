package com.example.server.routes

import com.example.server.data.repository.GymRepository
import com.example.server.models.entities.ExposedGym
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class CreateGymRequest(val name: String)


fun Route.gymRoutes() {
    val gymRepository = GymRepository()

    // Create exercise

    post("/gym/") {
        val req = call.receive<CreateGymRequest>()
        try {
//            val name = call.parameters["name"] ?: throw IllegalArgumentException("Invalid Name")
//            val description = call.parameters["description"] ?: ""
//            val totalNumberOfMachines = call.parameters["total_number_of_machines"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid total number of machines")
//            val numberOfMachinesAvailable = call.parameters["number_of_machines_available"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid number of machines available")
//            val queueSize = call.parameters["queue_size"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid Queue Size")
//            val gymId = call.parameters["gym_id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid Gym ID")
            val gym = gymRepository.createGym((ExposedGym(req.name)))
            call.respond(HttpStatusCode.Created, "User created successfully: ${gym.value}")
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Failed to create gym: ${e.localizedMessage}")
        }

    }
    // Delete exercise
    delete("/gym/") {
        val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID")
        gymRepository.deleteGym(id)
        call.respond(HttpStatusCode.OK)
    }
}
