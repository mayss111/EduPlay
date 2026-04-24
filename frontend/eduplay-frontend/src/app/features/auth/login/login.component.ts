import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {
  form: FormGroup;
  isLoading = false;
  errorMsg = '';
  selectedLanguage: 'FRENCH' | 'ARABIC';
  stars = Array.from({ length: 24 }, () => ({
    x: Math.random() * 100,
    y: Math.random() * 100,
    o: 0.35 + Math.random() * 0.65
  }));

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.selectedLanguage = this.authService.getUiLanguage();
    this.form = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  private languageSub?: Subscription;

  ngOnInit() {
    this.languageSub = this.authService.language$.subscribe(language => {
      this.selectedLanguage = language;
    });
  }

  ngOnDestroy() {
    this.languageSub?.unsubscribe();
  }

  get isArabic(): boolean {
    return this.selectedLanguage === 'ARABIC';
  }

  t(fr: string, ar: string): string {
    return this.isArabic ? ar : fr;
  }

  selectLanguage(language: 'FRENCH' | 'ARABIC') {
    this.selectedLanguage = language;
    this.authService.setUiLanguage(language);
  }

  onSubmit() {
    if (this.form.invalid) return;
    this.isLoading = true;
    this.errorMsg = '';

    const payload = {
      ...this.form.value,
      language: this.selectedLanguage
    };

    this.authService.login(payload).subscribe({
      next: () => this.router.navigate(['/dashboard']),
      error: (err) => {
        this.isLoading = false;
        this.errorMsg = this.resolveLoginErrorMessage(err);
      }
    });
  }

  private resolveLoginErrorMessage(err: any): string {
    const backendMessage = err?.error?.messages?.[0] || err?.error?.message || '';
    const raw = String(backendMessage).toLowerCase();

    if (raw.includes('database') || raw.includes('relation') || raw.includes('transaction')) {
      return this.t(
        'Le serveur est en cours de preparation. Reessaie dans quelques secondes.',
        'الخادم قيد التحضير. حاول مرة أخرى بعد ثوان قليلة.'
      );
    }

    return this.t('Pseudo ou mot de passe incorrect !', 'اسم المستخدم أو كلمة المرور غير صحيحة!');
  }
}