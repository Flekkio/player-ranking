import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Player, NewPlayer } from '../models/player.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class PlayerService {
  private apiUrl = 'http://localhost:8080/players';

  constructor(private http: HttpClient) {}

  getPlayers(): Observable<Player[]> {
    console.log('📡 Envoi requête GET vers', this.apiUrl);
    return this.http.get<Player[]>(this.apiUrl);
  }

  addPlayer(player: NewPlayer): Observable<Player> {
    return this.http.post<Player>(this.apiUrl, player);
  }

  deletePlayer(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  deleteAllPlayers(): Observable<void> {
    return this.http.delete<void>(this.apiUrl);
  }

  updatePlayerPoints(id: string, points: number): Observable<void> {
    const body = { points };
    return this.http.put<void>(`${this.apiUrl}/${id}`, body);
  }
}
