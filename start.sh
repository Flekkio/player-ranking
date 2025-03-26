#!/bin/bash

echo "Lancement du projet Player Ranking..."

echo "Démarrage de MongoDB avec Docker..."
docker-compose up -d

echo "Compilation du projet..."
./gradlew build || { echo "Erreur lors du build. Abort."; exit 1; }

echo "Démarrage du serveur Ktor..."
./gradlew run
