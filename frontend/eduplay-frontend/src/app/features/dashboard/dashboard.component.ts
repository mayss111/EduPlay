import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../core/services/auth.service';
import { HttpClient } from '@angular/common/http';
import { User } from '../../shared/models/user.model';
import { API_ENDPOINTS } from '../../core/constants/api.constants';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit, OnDestroy {

  user: User | null = null;
  leaderboard: any[] = [];
  selectedSubject = '';
  selectedDifficulty = '';

  get userLevel(): number {
    if (!this.user) return 1;
    // Simple level formula: Level 1 = 0-200 XP, Level 2 = 201-500 XP, Level 3 = 501-900 XP, etc.
    return Math.floor(Math.sqrt(this.user.totalXp / 100)) + 1;
  }

  get xpForNextLevel(): number {
    const nextLevel = this.userLevel;
    return Math.pow(nextLevel, 2) * 100;
  }

  get xpProgress(): number {
    if (!this.user) return 0;
    const currentLevelXp = Math.pow(this.userLevel - 1, 2) * 100;
    const nextLevelXp = this.xpForNextLevel;
    const progress = ((this.user.totalXp - currentLevelXp) / (nextLevelXp - currentLevelXp)) * 100;
    return Math.min(Math.max(progress, 0), 100);
  }

  avatars = ['🐰', '🦔', '🐿️', '🦁', '🐯', '🦊'];

  subjects = [
    { key: 'MATH',      label: 'Maths',      icon: '🔢', color: '#7c3aed' },
    { key: 'FRENCH',    label: 'Français',   icon: '📝', color: '#0ea5e9' },
    { key: 'ARABIC',    label: 'Arabe',      icon: '📖', color: '#10b981' },
    { key: 'SCIENCE',   label: 'Sciences',   icon: '🔬', color: '#f59e0b' },
    { key: 'HISTORY',   label: 'Histoire',   icon: '🏛️', color: '#ef4444' },
    { key: 'GEOGRAPHY', label: 'Géographie', icon: '🌍', color: '#8b5cf6' }
  ];

  difficulties = [
    { key: 'SIMPLE',    label: 'Simple',    stars: '⭐' },
    { key: 'MOYEN',     label: 'Moyen',     stars: '⭐⭐' },
    { key: 'DIFFICILE', label: 'Difficile', stars: '⭐⭐⭐' },
    { key: 'EXCELLENT', label: 'Excellent', stars: '⭐⭐⭐⭐' }
  ];

  constructor(
    private authService: AuthService,
    private router: Router,
    private http: HttpClient
  ) {}

  private languageSub?: Subscription;

  ngOnInit() {
    this.user = this.authService.getUser();
    this.languageSub = this.authService.language$.subscribe(() => {
      this.user = this.authService.getUser();
    });
    this.loadLeaderboard();
  }

  ngOnDestroy() {
    this.languageSub?.unsubscribe();
  }

  get isArabic(): boolean {
    return this.authService.getUiLanguage() === 'ARABIC';
  }

  t(fr: string, ar: string): string {
    return this.isArabic ? ar : fr;
  }

  selectLanguage(language: 'FRENCH' | 'ARABIC') {
    this.authService.setUiLanguage(language);
    this.user = this.authService.getUser();
  }

  subjectLabel(key: string): string {
    if (!this.isArabic) {
      return this.subjects.find(s => s.key === key)?.label || key;
    }
    return {
      MATH: 'رياضيات',
      FRENCH: 'فرنسية',
      ARABIC: 'عربية',
      SCIENCE: 'علوم',
      HISTORY: 'تاريخ',
      GEOGRAPHY: 'جغرافيا'
    }[key] || key;
  }

  difficultyLabel(key: string): string {
    if (!this.isArabic) {
      return this.difficulties.find(d => d.key === key)?.label || key;
    }
    return {
      SIMPLE: 'سهل',
      MOYEN: 'متوسط',
      DIFFICILE: 'صعب',
      EXCELLENT: 'ممتاز'
    }[key] || key;
  }

  loadLeaderboard() {
    this.http.get<any[]>(`${API_ENDPOINTS.leaderboard}/global`).subscribe({
      next: (data) => this.leaderboard = data.slice(0, 5),
      error: () => {}
    });
  }

  startGame() {
    if (!this.selectedSubject || !this.selectedDifficulty) return;
    this.router.navigate(['/game'], {
      queryParams: {
        subject: this.selectedSubject,
        difficulty: this.selectedDifficulty,
        language: this.authService.getUiLanguage()
      }
    });
  }

  logout() {
    this.authService.logout();
  }

  getXpPercent(): number {
    return Math.min(((this.user?.totalXp || 0) % 100), 100);
  }

  getLevel(): number {
    return Math.floor((this.user?.totalXp || 0) / 100) + 1;
  }
}