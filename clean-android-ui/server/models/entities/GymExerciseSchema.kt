package com.example.server.models.entities

import org.jetbrains.exposed.sql.transactions.transaction
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import com.example.server.models.entities.ExerciseService.Exercise
import com.example.server.models.entities.GymService.Gym

@Serializable
data class ExposedGymExercise(val user: Int, val workout: Int)
class GymExerciseService(private val database: Database) {
    // Many-to-Many relationship table between
    object GymExercise: Table() {
        val gym = reference("gym_id", Gym)
        val exercise = reference("exercise_id", Exercise)
        override val primaryKey = PrimaryKey(gym, exercise)
    }
    init {
        transaction(database) {
            SchemaUtils.create(GymExercise)
        }
    }
}