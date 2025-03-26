package com.flekkio.playeranking.repository

import com.flekkio.playeranking.models.Player
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq
import org.litote.kmongo.setValue
import org.litote.kmongo.descending

class PlayerRepository(database: CoroutineDatabase) {

    private val collection: CoroutineCollection<Player> = database.getCollection()

    suspend fun getAll(): List<Player> =
        collection.find().sort(descending(Player::points)).toList()

    suspend fun insert(player: Player): Player {
        collection.insertOne(player)
        return player
    }

    suspend fun update(id: String, newPoints: Int): Boolean {
        val result = collection.updateOne(Player::id eq id, setValue(Player::points, newPoints))
        return result.matchedCount > 0
    }

    suspend fun deleteById(id: String): Boolean {
        return try {
            val result = collection.deleteOne(Player::id eq id)
            result.deletedCount > 0
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteAll(): Boolean =
        collection.deleteMany("{}").wasAcknowledged()
}
