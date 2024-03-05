package com.example.server.data.repository

import com.example.server.models.entities.ExerciseService.Exercise
import com.example.server.models.entities.ExposedExercise
import com.example.server.routes.CreateExerciseRequest
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
                it[totalNumberOfMachines] = exercise.totalNumberOfMachines
                it[numberOfMachinesAvailable] = exercise.numberOfMachinesAvailable
                it[gymId] = exercise.gymId
                it[queueSize] = exercise.queueSize
            } get Exercise.id
        }
    }

    fun readExercise(gymId: Int, id: Int): ExposedExercise? {
        return transaction {
            Exercise.select { (Exercise.id eq id) and (Exercise.gymId eq gymId) }
                .mapNotNull { toExposedExercise(it) }
                .singleOrNull()
        }
    }

    fun readAllExercise(gymId: Int): List<ExposedExercise> {
        return transaction {
            Exercise.select { (Exercise.gymId eq gymId) }
                .mapNotNull { toExposedExercise(it) }

        }
    }

    fun updateExercise(id: Int, exercise: ExposedExercise) {
        transaction {
            Exercise.update({ Exercise.id eq id})  {
                it[name] = exercise.name
                it[description] = exercise.description
                it[totalNumberOfMachines] = exercise.totalNumberOfMachines
                it[numberOfMachinesAvailable] = exercise.numberOfMachinesAvailable
                it[gymId] = exercise.gymId
                it[queueSize] = exercise.queueSize
            }
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

    fun deleteExercise(gymId: Int, id: Int) {
        transaction {
            Exercise.deleteWhere { (Exercise.id eq id) and (Exercise.gymId eq gymId) }
        }
    }

    private fun toExposedExercise(row: ResultRow): ExposedExercise =
        ExposedExercise(
            name = row[Exercise.name],
            description = row[Exercise.description],
            totalNumberOfMachines = row[Exercise.totalNumberOfMachines],
            numberOfMachinesAvailable = row[Exercise.numberOfMachinesAvailable],
            gymId = row[Exercise.gymId],
            queueSize = row[Exercise.queueSize]
        )
}

// TODO: create endpoints for CreateExercise and UpdateExercise