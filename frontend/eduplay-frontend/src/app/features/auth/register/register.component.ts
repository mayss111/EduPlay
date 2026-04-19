import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  form: FormGroup;
  isLoading = false;
  errorMsg = '';
  selectedAvatar = 0;
  selectedClass = 0;
  selectedLanguage: 'FRENCH' | 'ARABIC' = 'FRENCH';

  avatars = ['🐰', '🦔', '🐿️', '🦁', '🐯', '🦊'];
  classes = [1, 2, 3, 4, 5, 6];

  stars = Array.from({ length: 30 }, () => ({
    x: Math.random() * 100,
    y: Math.random() * 100,
    o: +(Math.random() * 0.5 + 0.1).toFixed(2)
  }));

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.selectedLanguage = this.authService.getUiLanguage();
    this.form = this.fb.group({
      firstName:   ['', Validators.required],
      username:    ['', [Validators.required, Validators.minLength(3)]],
      password:    ['', [Validators.required, Validators.minLength(6)]],
      language:    [this.selectedLanguage, Validators.required],
      classLevel:  [null, Validators.required]
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
    this.form.patchValue({ language });
  }

  selectAvatar(i: number) {
    this.selectedAvatar = i;
  }

  selectClass(c: number) {
    this.selectedClass = c;
    this.form.patchValue({ classLevel: c });
  }

  getStrength(): number {
    const p = this.form.get('password')?.value || '';
    let s = 0;
    if (p.length >= 6) s += 30;
    if (p.length >= 8) s += 20;
    if (/[A-Z]/.test(p)) s += 20;
    if (/[0-9]/.test(p)) s += 20;
    if (/[^a-zA-Z0-9]/.test(p)) s += 10;
    return s;
  }

  getStrengthColor(): string {
    const s = this.getStrength();
    if (s < 40) return '#ef4444';
    if (s < 70) return '#fbbf24';
    return '#10b981';
  }

  onSubmit() {
    if (this.form.invalid || !this.selectedClass) {
      this.errorMsg = this.t('Remplis tous les champs et choisis ta classe !', 'املأ كل الحقول واختر قسمك!');
      return;
    }
    this.isLoading = true;
    this.errorMsg = '';

    const payload = { ...this.form.value, avatarIndex: this.selectedAvatar };

    this.authService.register(payload).subscribe({
      next: () => this.router.navigate(['/dashboard']),
      error: (err) => {
        this.isLoading = false;
        this.errorMsg = err.error?.message || this.t('Ce pseudo est déjà pris !', 'اسم المستخدم هذا مستعمل بالفعل!');
      }
    });
  }
}