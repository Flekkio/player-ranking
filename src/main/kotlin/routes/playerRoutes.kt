package com.flekkio.playeranking.routes

import com.flekkio.playeranking.repository.PlayerRepository
import com.flekkio.playeranking.models.Player
import com.flekkio.playeranking.dto.PlayerWithRank
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.http.*

fun Route.playerRoutes(repo: PlayerRepository) {

    route("/players") {
        
        get {
            val allPlayers = repo.getAll()
            call.respond(HttpStatusCode.OK, allPlayers)
        }

        get("{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID manquant")
                return@get
            }

            val allPlayersSorted = repo.getAll()
            val player = allPlayersSorted.find { it.id == id }

            if (player == null) {
                call.respond(HttpStatusCode.NotFound, "Joueur introuvable")
            } else {
                val rank = allPlayersSorted.indexOf(player) + 1
                val playerWithRank = PlayerWithRank(
                    id = player.id,
                    pseudo = player.pseudo,
                    points = player.points,
                    rank = rank
                )
                call.respond(HttpStatusCode.OK, playerWithRank)
            }
        }

        get("/ranking") {
            val sortedPlayers = repo.getAll()
            val ranked = sortedPlayers.mapIndexed { index, player ->
                PlayerWithRank(
                    id = player.id,
                    pseudo = player.pseudo,
                    points = player.points,
                    rank = index + 1
                )
            }
            call.respond(HttpStatusCode.OK, ranked)
        }

        post {
            val player = call.receive<Player>()
            val created = repo.insert(player)
            call.respond(HttpStatusCode.Created, created)
        }

        put("{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID manquant")
                return@put
            }

            val body = call.receive<Map<String, Int>>()
            val newPoints = body["points"]
            if (newPoints == null) {
                call.respond(HttpStatusCode.BadRequest, "Champ 'points' manquant")
                return@put
            }

            val updated = repo.update(id, newPoints)
            if (updated) {
                call.respond(HttpStatusCode.OK, "Joueur mis à jour")
            } else {
                call.respond(HttpStatusCode.NotFound, "Joueur introuvable")
            }
        }

        delete {
            val success = repo.deleteAll()
            if (success) {
                call.respond(HttpStatusCode.OK, "Tous les joueurs ont été supprimés")
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Erreur lors de la suppression")
            }
        }

        delete("{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID manquant")
                return@delete
            }

            val success = repo.deleteById(id)
            if (success) {
                call.respond(HttpStatusCode.OK, "Joueur supprimé")
            } else {
                call.respond(HttpStatusCode.NotFound, "Joueur introuvable")
            }
        }
    }
}