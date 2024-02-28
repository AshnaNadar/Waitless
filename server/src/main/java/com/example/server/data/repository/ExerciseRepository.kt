package com.example.server.data.repository

import com.example.server.models.entities.ExerciseService.Exercise
import com.example.server.models.entities.ExposedExercise
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ExerciseRepository {
    fun createExercise(exercise: ExposedExercise): EntityID<Int> {
        return transaction {
            Exercise.insert {
                it[name] = exercise.name
                it[description] = exercise.description
            } get Exercise.id
        }
    }

    fun readExercise(id: Int): ExposedExercise? {
        return transaction {
            Exercise.select { Exercise.id eq id }
                .mapNotNull { toExposedExercise(it) }
                .singleOrNull()
        }
    }

    fun updateUser(id: Int, user: ExposedExercise) {
        transaction {
            Exercise.update({ Exercise.id eq id }) {
                it[name] = user.name
                it[description] = user.description
            }
        }
    }

    fun deleteExercise(id: Int) {
        transaction {
            Exercise.deleteWhere { Exercise.id eq id }
        }
    }

    private fun toExposedExercise(row: ResultRow): ExposedExercise =
        ExposedExercise(
            name = row[Exercise.name],
            description = row[Exercise.description]
        )
}
