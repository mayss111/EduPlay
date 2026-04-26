import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit, OnDestroy {
  form: FormGroup;
  isLoading = false;
  errorMsg = '';
  showPassword = false;
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

  private languageSub?: Subscription;

  ngOnInit() {
    this.languageSub = this.authService.language$.subscribe(language => {
      this.selectedLanguage = language;
      this.form.patchValue({ language }, { emitEvent: false });
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
        this.errorMsg = this.resolveRegisterErrorMessage(err);
      }
    });
  }

  private resolveRegisterErrorMessage(err: any): string {
    const first = err?.error?.messages?.[0] || err?.error?.message || '';
    const msg = String(first);
    const lowered = msg.toLowerCase();

    if (lowered.includes('already') || lowered.includes('deja') || lowered.includes('pris') || lowered.includes('unique')) {
      return this.t('Ce pseudo est deja pris. Essaie un autre pseudo.', 'اسم المستخدم هذا مستخدم بالفعل. جرب اسما آخر.');
    }
    if (lowered.includes('database') || lowered.includes('relation') || lowered.includes('transaction')) {
      return this.t(
        'Le serveur est en cours de preparation. Reessaie dans quelques secondes.',
        'الخادم قيد التحضير. حاول مرة أخرى بعد ثوان قليلة.'
      );
    }
    if (lowered.includes('minimum 6')) {
      return this.t('Le mot de passe doit contenir au moins 6 caracteres.', 'يجب أن تحتوي كلمة المرور على 6 أحرف على الأقل.');
    }

    return msg || this.t('Impossible de creer le compte pour le moment.', 'تعذر إنشاء الحساب حاليا.');
  }
}