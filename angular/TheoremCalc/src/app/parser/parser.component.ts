import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http'; 
import { Observable } from 'rxjs';

@Component({
  selector: 'app-parser',
  standalone: true,
  imports: [FormsModule, NgIf, HttpClientModule, CommonModule],
  templateUrl: './parser.component.html',
  styleUrl: './parser.component.css'
})

export class ParserComponent {
  exp: string | null = null;
  result: string | null = null;
  error: string | null = null;

  private apiUrl = 'https://theoremcalc.onrender.com/api/parse'; 

  constructor(private http: HttpClient) {}

  populateInput(expression: string) {
    this.exp = expression;
  }

  onSubmit() {
    this.error = null;
    this.result = null;
    if (this.exp !== null) {
      console.log('Request body:', { exp: this.exp });
      this.getTree(this.exp).subscribe({
          next: (response) => {
            console.log('Response from server:', response);
            if (!response || response.length === 0) { 
              this.result = null;
              this.error = 'No prime numbers found in the given range';
            } else {
              this.result = response; 
            }
          },
          error: (error) => {
            console.error('Error', error);
            console.error('Error status:', error.status);
            console.error('Error body:', error.error);
            this.error = 'An error occurred while processing your request';
          }
      });
    }
  }
  
  getTree(exp: string): Observable<string> {
    const body = { exp };
    return this.http.post(this.apiUrl, body, { responseType: 'text' });
  }
}

