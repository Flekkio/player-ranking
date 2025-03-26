import { Component, OnInit } from '@angular/core';
import { PlayerService } from './services/player.service';
import { Player } from './models/player.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { catchError, of } from 'rxjs';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  players: Player[] = [];
  newPseudo: string = '';
  newPoints: number = 0;
  editingId: string | null = null;
  editedPoints: number = 0;

  constructor(private playerService: PlayerService) {}

  ngOnInit(): void {
    this.loadPlayers();
  }

  loadPlayers(): void {
    this.playerService.getPlayers().subscribe({
      next: (data) => {
        this.players = data;
      },
      error: (err) => console.error('Erreur de chargement des joueurs :', err)
    });
  }

  deletePlayer(id: string): void {
    this.playerService.deletePlayer(id)
      .pipe(
        catchError(error => {
          console.error('Erreur lors de la suppression :', error);
          return of(null);
        })
      )
      .subscribe({
        next: (response) => {
          if (response !== null) {
            this.players = this.players.filter(player => player.id !== id);
            console.log('Joueur supprimé avec succès');
          }

          this.loadPlayers();
        }
      });
  }

  addPlayer(): void {
    if (!this.newPseudo.trim()) return;

    const newPlayer = {
      pseudo: this.newPseudo.trim(),
      points: this.newPoints
    };

    this.playerService.addPlayer(newPlayer).subscribe({
      next: () => {
        this.newPseudo = '';
        this.newPoints = 0;
        this.loadPlayers();
      },
      error: (err) => {
        console.error('Erreur lors de l’ajout du joueur :', err);
      }
    });
  }

  endTournament(): void {
    console.log('Suppression de tous les joueurs demandée');

    this.playerService.deleteAllPlayers()
      .pipe(
        catchError(error => {
          console.error('Erreur lors de la suppression :', error);
          return of(null);
        })
      )
      .subscribe({
        next: (response) => {
          if (response !== null) {
            console.log('Tous les joueurs ont été supprimés');
          }

          this.loadPlayers();
        }
      });
  }

  updatePoints(): void {
    if (!this.editingId) return;

    this.playerService.updatePlayerPoints(this.editingId, this.editedPoints)
      .pipe(
        catchError(error => {
          console.error('Erreur lors de la mise à jour des points :', error);
          return of(null);
        })
      )
      .subscribe({
        next: (res) => {
          if (res !== null) {
          }
          this.editingId = null;
          this.editedPoints = 0;
          this.loadPlayers();
        }
      });
  }

  startEdit(player: Player): void {
    this.editingId = player.id;
    this.editedPoints = player.points;
  }

  cancelEdit(): void {
    this.editingId = null;
    this.editedPoints = 0;
  }
}
