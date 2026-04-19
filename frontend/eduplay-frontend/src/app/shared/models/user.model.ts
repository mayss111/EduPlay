export interface User {
  username: string;
  firstName: string;
  role: 'STUDENT';
  language: 'FRENCH' | 'ARABIC';
  classLevel: number;
  avatarIndex: number;
  totalXp: number;
}

export interface AuthResponse {
  token: string;
  username: string;
  firstName: string;
  role: 'STUDENT';
  language: 'FRENCH' | 'ARABIC';
  classLevel: number;
  avatarIndex: number;
  totalXp: number;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  firstName: string;
  username: string;
  password: string;
  language: 'FRENCH' | 'ARABIC';
  classLevel: number;
  avatarIndex: number;
}