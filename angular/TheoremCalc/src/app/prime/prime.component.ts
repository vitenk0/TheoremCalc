import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http'; 
import { catchError, Observable, throwError } from 'rxjs';
import { apiResponse } from '../api-response.model';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-prime',
  standalone: true,
  imports: [FormsModule, NgIf, HttpClientModule, CommonModule],
  templateUrl: './prime.component.html',
  styleUrl: './prime.component.css'
})

export class PrimeComponent {
  start: number | null = null;
  end: number | null = null;
  result: string | null = null;
  error: string | null = null;

  //Testing
  //private apiUrl = 'http://localhost:8080';
  //Prod
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  onSubmit() {
    this.error = null;
    this.result = null;

    if (this.start == null || this.end == null) {
      this.error = 'Enter a start and end number';
    } else {
      this.getNumbersInRange(this.start, this.end).subscribe({
        next: (response) => {
          if(response.data){
            this.result = response.data.join(', ');
          }
        },
        error: (errorResponse) => {
          this.error = errorResponse.message;
        }
      });
    }
  }

  getNumbersInRange(start: number, end: number): Observable<apiResponse<number[]>> {
    const body = { start, end };
    return this.http.post<apiResponse<number[]>>(this.apiUrl + "/api/prime", body)
    .pipe(
      catchError((error) => {
        console.error('HTTP Error:', error);
        const errorMessage = error.error?.error || "An unexpected error occurred";
        return throwError(() => new Error(errorMessage));
      })
    );
  }
}
