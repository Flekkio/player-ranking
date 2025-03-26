package com.flekkio.playeranking

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.http.*

import com.flekkio.playeranking.routes.playerRoutes
import com.flekkio.playeranking.repository.PlayerRepository
import com.flekkio.playeranking.database.DatabaseFactory
import kotlinx.serialization.json.Json

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {

    val database = DatabaseFactory.init()
    val repo = PlayerRepository(database)

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }

    install(CORS) {
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        anyHost()
    }

    routing {
        playerRoutes(repo)
    }
}