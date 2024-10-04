import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http'; 
import { catchError, Observable, throwError } from 'rxjs';
import { AfterViewInit } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Inject, PLATFORM_ID } from '@angular/core';
import { apiResponse } from '../api-response.model';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-parser',
  standalone: true,
  imports: [FormsModule, NgIf, HttpClientModule, CommonModule],
  templateUrl: './parser.component.html',
  styleUrl: './parser.component.css'
})

export class ParserComponent implements AfterViewInit {
  exp: string | null = null;
  tree: string | null = null;
  error: string | null = null;
  latex_exp: string | null = null;

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient, @Inject(PLATFORM_ID) private platformId: any) {}

  populateInput(expression: string) {
    this.exp = expression;
  }

  onSubmit() {
    this.error = null;
    this.tree = null;
    this.latex_exp = null;

    if(this.exp === null || this.exp === '') {
      this.error = 'Enter an expression';
    } else {
      this.getTree(this.exp).subscribe({
          next: (response) => {
            if(response.data){
              this.tree = response.data;
            }
            this.getLaTex(this.exp!).subscribe({
              next: (latexResponse) => {
                if(latexResponse.data){
                  this.latex_exp = latexResponse.data; 
                  this.renderMathJax();
                }
              },
              error: (errorResponse) => {
                this.error = errorResponse.message;
              }
            });
          },
          error: (errorResponse) => {
            this.error = errorResponse.message;
          }
      });
    }
  }
  
  getTree(exp: string): Observable<apiResponse<string>> {
    const body = { exp };
    return this.http.post<apiResponse<string>>(this.apiUrl + "/api/parse", body)
    .pipe(
      catchError((error) => {
        console.error('HTTP Error:', error);
        const errorMessage = error.error?.error || "An unexpected error occurred";
        return throwError(() => new Error(errorMessage));
      })
    );
  }

  getLaTex(exp: string): Observable<apiResponse<string>> {
    const body = { exp };
    return this.http.post<apiResponse<string>>(this.apiUrl + "/api/latex", body)
    .pipe(
      catchError((error) => {
        console.error('HTTP Error:', error);
        const errorMessage = error.error?.error || "An unexpected error occurred";
        return throwError(() => new Error(errorMessage));
      })
    );
  }

  ngAfterViewInit(): void {
    this.loadMathJax();
    this.renderMathJax();
  }

  ngAfterViewChecked(): void {
    this.renderMathJax();
  } 

  loadMathJax() {
    if (isPlatformBrowser(this.platformId)) {
        if (!window.MathJax) {
            const script = document.createElement('script');
            script.type = 'text/javascript';
            script.src = 'https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js';
            script.async = true;
            script.onload = () => {
                this.renderMathJax();
            };
            document.head.appendChild(script);
        } else {
            this.renderMathJax();
        }
    }
}

  renderMathJax() {
    if (isPlatformBrowser(this.platformId) && this.exp) {
      window.MathJax.typesetPromise();
    }
  }
}

declare global {
  interface Window {
    MathJax: any;
  }
}

