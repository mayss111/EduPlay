import { Component, OnDestroy, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from './core/services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'eduplay-frontend';
  private languageSub?: Subscription;

  constructor(private authService: AuthService) {}

  ngOnInit() {
    this.applyLanguage(this.authService.getUiLanguage());
    this.languageSub = this.authService.language$.subscribe(language => {
      this.applyLanguage(language);
    });
  }

  ngOnDestroy() {
    this.languageSub?.unsubscribe();
  }

  private applyLanguage(language: 'FRENCH' | 'ARABIC') {
    const isArabic = language === 'ARABIC';
    document.documentElement.lang = isArabic ? 'ar' : 'fr';
    document.documentElement.dir = isArabic ? 'rtl' : 'ltr';
    document.body.dir = isArabic ? 'rtl' : 'ltr';
  }
}
