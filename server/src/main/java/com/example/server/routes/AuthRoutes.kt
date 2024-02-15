package com.example.server.routes
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.client.request.*
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.Logging
import kotlinx.serialization.Serializable
import io.ktor.client.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.statement.*

@Serializable
data class SignUpRequest(
    val email: String,
    val password: String
)

@Serializable
data class SignInRequest(
    val email: String,
    val password: String,
)

fun Route.authRoutes(supabaseUrl: String, supabaseAnonKey: String) {
    val client = HttpClient(CIO) {
        install(Logging)
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    post("/auth/signup") {
        val signUpRequest = call.receive<SignUpRequest>()

        val response: HttpResponse = client.post("${supabaseUrl}/auth/v1/signup") {
            headers {
                append("apikey", supabaseAnonKey)
                append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            }
            setBody(signUpRequest)
        }
        // Handling response generically
        val responseBody = response.bodyAsText()
        call.respondText(responseBody, ContentType.Application.Json, response.status)
    }

    post("/auth/signin") {
        val signInRequest = call.receive<SignInRequest>()
        val response: HttpResponse = client.post("${supabaseUrl}/auth/v1/token") {
            headers {
                append("apikey", supabaseAnonKey)
                append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            }
            setBody(signInRequest)
        }
        // Handling response generically
        val responseBody = response.bodyAsText()
        call.respondText(responseBody, ContentType.Application.Json, response.status)
    }

    post("/auth/signout") {
        // Supabase doesn't provide a sign-out API endpoint since it uses JWT tokens.
        // Tokens are stored client-side, so "signing out" typically involves the client
        // deleting the token from storage.
        call.respond(HttpStatusCode.OK, "Signed out successfully")
    }
}
