package com.example.server.models.entities

import org.jetbrains.exposed.sql.transactions.transaction
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import com.example.server.models.entities.UserService.User
import com.example.server.models.entities.GymService.Gym

@Serializable
data class ExposedGymAdmin(val user: Int, val workout: Int)
class GymAdminService(private val database: Database) {
    // Many-to-Many relationship table between Gyms and Admins
    object GymAdmin: Table() {
        val gym = reference("gym_id", Gym)
        val user = reference("user_id", User)
        override val primaryKey = PrimaryKey(gym, user)
    }
    init {
        transaction(database) {
            SchemaUtils.create(GymAdmin)
        }
    }
}