package com.example.server.routes

import com.example.server.data.repository.ExerciseRepository
import com.example.server.models.entities.ExposedExercise
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class CreateExerciseRequest(val name: String, val description: String, val totalNumberOfMachines: Int, val numberOfMachinesAvailable: Int, val queueSize: Int, val gymId: Int)


fun Route.exerciseRoutes() {
    val exerciseRepository = ExerciseRepository()

    // Create exercise

    post("/exercise/") {
        try {
//            val exercise = call.receive<CreateExerciseRequest>()
//            val name = call.parameters["name"] ?: throw IllegalArgumentException("Invalid Name")
//            val description = call.parameters["description"] ?: ""
//            val totalNumberOfMachines = call.parameters["total_number_of_machines"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid total number of machines")
//            val numberOfMachinesAvailable = call.parameters["number_of_machines_available"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid number of machines available")
//            val queueSize = call.parameters["queue_size"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid Queue Size")
//            val gymId = call.parameters["gym_id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid Gym ID")
            val body = call.receive<ExposedExercise>()
            print(body)
            val exercise = exerciseRepository.createExercise(body)
            call.respond(HttpStatusCode.Created, "User created successfully: ${exercise.value}")
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Failed to create user record: ${e.localizedMessage}")
        }

    }

    // Read specific exercise
    get("/exercise/") {
        val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID")
        val gymId = call.parameters["gym_id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid Gym ID")
        val exercise = exerciseRepository.readExercise(gymId, id)
        if (exercise != null) {
            call.respond(HttpStatusCode.OK, exercise)
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    }

    // Read all exercises from gym
    get("/exercise/all/") {
        val gymId = call.parameters["gym_id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid Gym ID")
        val exercises = exerciseRepository.readAllExercise(gymId)
        if (exercises.isNotEmpty()) {
            call.respond(HttpStatusCode.OK, exercises)
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    }

    // Update exercise
    put("/exercise/") {
        val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID")
        val exercise = call.receive<ExposedExercise>()
        exerciseRepository.updateExercise(id, exercise)
        call.respond(HttpStatusCode.OK)
    }

    // Delete exercise
    delete("/exercise/") {
        val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID")
        val gymId = call.parameters["gym_id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid Gym ID")
        exerciseRepository.deleteExercise(gymId, id)
        call.respond(HttpStatusCode.OK)
    }
}
