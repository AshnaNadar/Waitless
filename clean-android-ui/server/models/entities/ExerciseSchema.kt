package com.example.server.models.entities

import org.jetbrains.exposed.sql.transactions.transaction
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*

@Serializable
data class ExposedExercise(val name: String, val description: String?)
class ExerciseService(private val database: Database) {
    object Exercise : IntIdTable() {
        val name = varchar("name", length = 255)
        val description = varchar("description", 1000).nullable()
    }

    init {
        transaction(database) {
            SchemaUtils.create(Exercise)
        }
    }
}