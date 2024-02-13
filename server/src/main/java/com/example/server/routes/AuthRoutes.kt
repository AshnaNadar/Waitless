package com.example.server.routes
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Route.authRoutes() {
    post("/login") {
        // Handle login
    }

    post("/signup") {
        // Handle signup
    }
}
