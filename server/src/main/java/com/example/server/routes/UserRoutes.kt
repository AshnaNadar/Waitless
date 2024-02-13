package com.example.server.routes

import com.example.server.data.repository.UserRepository
import com.example.server.models.entities.ExposedUser
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes() {
    val userRepository = UserRepository()

    // Create user
    post("/users") {
        val user = call.receive<ExposedUser>()
        val id = userRepository.createUser(user)
        call.respond(HttpStatusCode.Created, id)
    }

    // Read user
    get("/users/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID")
        val user = userRepository.readUser(id)
        if (user != null) {
            call.respond(HttpStatusCode.OK, user)
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    }

    // Update user
    put("/users/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID")
        val user = call.receive<ExposedUser>()
        userRepository.updateUser(id, user)
        call.respond(HttpStatusCode.OK)
    }

    // Delete user
    delete("/users/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Invalid ID")
        userRepository.deleteUser(id)
        call.respond(HttpStatusCode.OK)
    }
}
