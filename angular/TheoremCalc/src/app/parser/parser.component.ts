import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http'; 
import { Observable } from 'rxjs';
import { AfterViewInit } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Inject, PLATFORM_ID } from '@angular/core';

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

  //Testing
  //private apiUrl = 'http://localhost:8080';
  //Prod
  private apiUrl = 'https://theoremcalc.onrender.com';

  constructor(private http: HttpClient, @Inject(PLATFORM_ID) private platformId: any) {}

  populateInput(expression: string) {
    this.exp = expression;
  }

  onSubmit() {
    this.error = null;
    this.tree = null;
    this.latex_exp = null;

    if(this.exp === null) {
      this.error = 'Enter an expression';
    } else {
      this.getTree(this.exp).subscribe({
          next: (response) => {
            this.tree = response;
            this.getLaTex(this.exp!).subscribe({
              next: (latexResponse) => {
                this.latex_exp = latexResponse; 
                this.renderMathJax();
              },
              error: (errorResponse) => {
                this.error = typeof errorResponse.error === 'string' ? errorResponse.error : "An error occurred";
              }
            });
          },
          error: (errorResponse) => {
            this.error = typeof errorResponse.error === 'string' ? errorResponse.error : "An error occurred";
          }
      });
    }
  }
  
  getTree(exp: string): Observable<string> {
    const body = { exp };
    return this.http.post(this.apiUrl + '/api/parse', body, { responseType: 'text' });
  }

  getLaTex(exp: string): Observable<string> {
    const body = { exp };
    return this.http.post(this.apiUrl + '/api/latex', body, { responseType: 'text' });
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

