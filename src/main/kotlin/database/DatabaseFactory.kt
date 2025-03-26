package com.flekkio.playeranking.database

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

object DatabaseFactory {
    fun init(): CoroutineDatabase {
        val client = KMongo.createClient().coroutine
        return client.getDatabase("playerdb")
    }
}