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