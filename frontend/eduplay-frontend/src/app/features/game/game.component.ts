import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Question, GameResult } from '../../shared/models/question.model';
import { API_ENDPOINTS } from '../../core/constants/api.constants';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent implements OnInit, OnDestroy {

  questions: Question[] = [];
  currentIndex = 0;
  selectedAnswer = '';
  showResult = false;
  isCorrect = false;
  score = 0;
  isLoading = true;
  isGameOver = false;
  gameResult: GameResult | null = null;
  timeLeft = 30;
  timer: any;

  subject = '';
  difficulty = '';

  answers: string[] = [];
  language: 'FRENCH' | 'ARABIC' = 'FRENCH';
  skillsWorked: Map<string, number> = new Map();
  currentCompetency = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.language = this.authService.getUser()?.language || this.authService.getUiLanguage();
    this.route.queryParams.subscribe(params => {
      this.subject    = params['subject'];
      this.difficulty = params['difficulty'];
      this.loadQuestions();
    });
  }

  get isArabic(): boolean {
    return this.language === 'ARABIC';
  }

  t(fr: string, ar: string): string {
    return this.isArabic ? ar : fr;
  }

  loadQuestions() {
    this.isLoading = true;
    const url = `${API_ENDPOINTS.game}/questions?subject=${this.subject}&difficulty=${this.difficulty}`;
    this.http.get<Question[]>(url).subscribe({
      next: (data) => {
        this.questions = data;
        this.isLoading = false;
        this.startTimer();
      },
      error: () => {
        this.isLoading = false;
        this.router.navigate(['/dashboard']);
      }
    });
  }

  get currentQuestion(): Question {
    return this.questions[this.currentIndex];
  }

  get currentCompetencyText(): string {
    return this.t(
      this.getCompetencyFR(this.subject, this.difficulty),
      this.getCompetencyAR(this.subject, this.difficulty)
    );
  }

  getCompetencyFR(subject: string, difficulty: string): string {
    const map: { [key: string]: string } = {
      'MATH_SIMPLE': 'calcul simple',
      'MATH_MOYEN': 'calcul intermédiaire',
      'MATH_DIFFICILE': 'résolution de problème',
      'FRENCH_SIMPLE': 'vocabulaire basique',
      'FRENCH_MOYEN': 'grammaire appliquée',
      'FRENCH_DIFFICILE': 'analyse textuelle',
      'SCIENCE_SIMPLE': 'concepts de base',
      'SCIENCE_MOYEN': 'explicación scientifique',
      'SCIENCE_DIFFICILE': 'expérimentation',
      'HISTORY_SIMPLE': 'repères historiques',
      'HISTORY_MOYEN': 'contextualisation',
      'HISTORY_DIFFICILE': 'analyse causale',
      'GEOGRAPHY_SIMPLE': 'géographie générale',
      'GEOGRAPHY_MOYEN': 'lecture de carte',
      'GEOGRAPHY_DIFFICILE': 'analyse régionale'
    };
    return map[`${subject}_${difficulty}`] || 'compétence';
  }

  getCompetencyAR(subject: string, difficulty: string): string {
    const map: { [key: string]: string } = {
      'MATH_SIMPLE': 'حساب بسيط',
      'MATH_MOYEN': 'حساب وسيط',
      'MATH_DIFFICILE': 'حل المسائل',
      'FRENCH_SIMPLE': 'مفردات أساسية',
      'FRENCH_MOYEN': 'قواعد لغوية',
      'FRENCH_DIFFICILE': 'تحليل نصي',
      'SCIENCE_SIMPLE': 'مفاهيم أساسية',
      'SCIENCE_MOYEN': 'شرح علمي',
      'SCIENCE_DIFFICILE': 'التجريب',
      'HISTORY_SIMPLE': 'معالم تاريخية',
      'HISTORY_MOYEN': 'السياق التاريخي',
      'HISTORY_DIFFICILE': 'التحليل السببي',
      'GEOGRAPHY_SIMPLE': 'جغرافيا عامة',
      'GEOGRAPHY_MOYEN': 'قراءة الخرائط',
      'GEOGRAPHY_DIFFICILE': 'تحليل إقليمي'
    };
    return map[`${subject}_${difficulty}`] || 'مهارة';
  }

  get choices(): { key: string; label: string }[] {
    if (!this.currentQuestion) return [];
    return [
      { key: 'A', label: this.currentQuestion.choiceA },
      { key: 'B', label: this.currentQuestion.choiceB },
      { key: 'C', label: this.currentQuestion.choiceC },
      { key: 'D', label: this.currentQuestion.choiceD }
    ];
  }

  startTimer() {
    this.timeLeft = 30;
    clearInterval(this.timer);
    this.timer = setInterval(() => {
      this.timeLeft--;
      if (this.timeLeft <= 0) {
        clearInterval(this.timer);
        if (!this.showResult) this.selectAnswer('');
      }
    }, 1000);
  }

  selectAnswer(key: string) {
    if (this.showResult) return;
    clearInterval(this.timer);
    this.selectedAnswer = key;
    this.isCorrect = key === this.currentQuestion.correctChoice;
    if (this.isCorrect) this.score++;
    this.showResult = true;
    this.answers.push(key);
  }

  next() {
    this.showResult = false;
    this.selectedAnswer = '';

    if (this.currentIndex < this.questions.length - 1) {
      this.currentIndex++;
      this.startTimer();
    } else {
      this.endGame();
    }
  }

  endGame() {
    clearInterval(this.timer);
    this.isGameOver = true;
    this.submitScore();
  }

  submitScore() {
    this.http.post<GameResult>(`${API_ENDPOINTS.game}/submit`, {
      correctAnswers: this.score,
      totalQuestions: this.questions.length,
      subject: this.subject,
      difficulty: this.difficulty
    }).subscribe({
      next: (result) => {
        this.gameResult = result;
        this.authService.updateLocalUserStats({ totalXp: result.totalXp, streak: result.streak });
      },
      error: () => {}
    });
  }

  getScorePercent(): number {
    return Math.round((this.score / this.questions.length) * 100);
  }

  getScoreEmoji(): string {
    const p = this.getScorePercent();
    if (p === 100) return '🏆';
    if (p >= 80)  return '🌟';
    if (p >= 60)  return '👍';
    if (p >= 40)  return '😅';
    return '💪';
  }

  goToDashboard() {
    this.router.navigate(['/dashboard']);
  }

  ngOnDestroy() {
    clearInterval(this.timer);
  }
}