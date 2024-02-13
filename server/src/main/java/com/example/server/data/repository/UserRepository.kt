package com.example.server.data.repository

import com.example.server.models.entities.UserService.Users
import com.example.server.models.entities.ExposedUser
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {
    fun createUser(user: ExposedUser): Int {
        return transaction {
            Users.insert {
                it[name] = user.name
                // You should hash the password here
            } get Users.id
        }
    }

    fun readUser(id: Int): ExposedUser? {
        return transaction {
            Users.select { Users.id eq id }
                .mapNotNull { toExposedUser(it) }
                .singleOrNull()
        }
    }

    fun updateUser(id: Int, user: ExposedUser) {
        transaction {
            Users.update({ Users.id eq id }) {
                it[name] = user.name
                // Remember to hash the password if it's being updated
            }
        }
    }

    fun deleteUser(id: Int) {
        transaction {
            Users.deleteWhere { Users.id eq id }
        }
    }

    private fun toExposedUser(row: ResultRow): ExposedUser =
        ExposedUser(
            name = row[Users.name]
        )
}
