package com.example.server.data.repository

import com.example.server.models.entities.ExposedGym
import com.example.server.models.entities.ExposedGymAdmin
import com.example.server.models.entities.GymAdminService.GymAdmin
import com.example.server.models.entities.GymService.Gym
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
class GymAdminRepository {
    fun createGymAdmin(gymAdmin: ExposedGymAdmin): EntityID<Int> {
        return transaction {
            GymAdmin.insert {
                it[user] = gymAdmin.user
                it[gym] = gymAdmin.gym
            } get GymAdmin.user
        }
    }

    fun deleteGymAdmin( gym: Int, user: Int) {
        transaction {
            GymAdmin.deleteWhere { (GymAdmin.user eq user) and (GymAdmin.gym eq gym) }
        }
    }
}