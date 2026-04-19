import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { AuthResponse, LoginRequest, RegisterRequest, User } from '../../shared/models/user.model';
import { API_ENDPOINTS } from '../constants/api.constants';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private API = API_ENDPOINTS.auth;
  private readonly UI_LANG_KEY = 'uiLanguage';

  constructor(private http: HttpClient, private router: Router) {}

  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.API}/login`, credentials).pipe(
      tap(res => this.saveSession(res))
    );
  }

  register(data: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.API}/register`, data).pipe(
      tap(res => this.saveSession(res))
    );
  }

  private saveSession(res: AuthResponse): void {
    localStorage.setItem('token', res.token);
    this.setUiLanguage(res.language);
    localStorage.setItem('user', JSON.stringify({
      username: res.username,
      firstName: res.firstName,
      role: res.role,
      language: res.language,
      classLevel: res.classLevel,
      avatarIndex: res.avatarIndex,
      totalXp: res.totalXp
    }));
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    this.router.navigate(['/login']);
  }

  getUiLanguage(): 'FRENCH' | 'ARABIC' {
    const lang = localStorage.getItem(this.UI_LANG_KEY);
    return lang === 'ARABIC' ? 'ARABIC' : 'FRENCH';
  }

  setUiLanguage(language: 'FRENCH' | 'ARABIC'): void {
    localStorage.setItem(this.UI_LANG_KEY, language);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getUser(): User | null {
    const u = localStorage.getItem('user');
    if (!u) return null;
    try {
      return JSON.parse(u) as User;
    } catch {
      return null;
    }
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    if (!token) return false;
    try {
      const payloadPart = token.split('.')[1];
      const normalized = payloadPart.replace(/-/g, '+').replace(/_/g, '/');
      const payload = JSON.parse(atob(normalized.padEnd(Math.ceil(normalized.length / 4) * 4, '=')));
      return payload.exp > Date.now() / 1000;
    } catch {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      return false;
    }
  }
}