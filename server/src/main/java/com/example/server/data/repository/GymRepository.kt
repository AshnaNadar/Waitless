package com.example.server.data.repository

import com.example.server.models.entities.ExerciseService.Exercise
import com.example.server.models.entities.ExposedGym
import com.example.server.models.entities.GymService.Gym
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
class GymRepository {
    fun createGym(gym: ExposedGym): EntityID<Int> {
        return transaction {
            Gym.insert {
                it[name] = gym.name
            } get Gym.id
        }
    }

    fun deleteGym( id: Int) {
        transaction {
            Gym.deleteWhere { Gym.id eq id }
        }
    }
}