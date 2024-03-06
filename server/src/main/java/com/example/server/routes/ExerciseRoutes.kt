package com.example.server.routes

import com.example.server.data.repository.ExerciseRepository
import com.example.server.models.entities.ExposedExercise
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
fun Route.exerciseRoutes() {
    val exerciseRepository = ExerciseRepository()

    // Create exercise
    post("{gym_id}/exercise") {
        try {
            val body = call.receive<ExposedExercise>()
            val exercise = exerciseRepository.createExercise(body)
            call.respond(HttpStatusCode.Created, "Exercise created successfully: ${exercise.value}")
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Failed to create exercise: ${e.localizedMessage}")
        }

    }

    // Read specific exercise
    get("{gym_id}/exercise/{id}") {
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
    get("{gym_id}/exercise/all") {
        val gymId = call.parameters["gym_id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid Gym ID")
        val exercises = exerciseRepository.readAllExercise(gymId)
        if (exercises.isNotEmpty()) {
            call.respond(HttpStatusCode.OK, exercises)
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    }

    // Update exercise
    put("{gym_id}/exercise/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID")
        val exercise = call.receive<ExposedExercise>()
        exerciseRepository.updateExercise(id, exercise)
        call.respond(HttpStatusCode.OK)
    }

    // Delete exercise
    delete("{gym_id}/exercise/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID")
        val gymId = call.parameters["gym_id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid Gym ID")
        exerciseRepository.deleteExercise(gymId, id)
        call.respond(HttpStatusCode.OK)
    }
}
