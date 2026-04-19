import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
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

    this.authService.login(this.form.value).subscribe({
      next: () => this.router.navigate(['/dashboard']),
      error: () => {
        this.isLoading = false;
        this.errorMsg = this.t('Pseudo ou mot de passe incorrect !', 'اسم المستخدم أو كلمة المرور غير صحيحة!');
      }
    });
  }
}