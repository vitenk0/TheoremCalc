import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http'; 
import { Observable } from 'rxjs';

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

  private apiUrl = 'http://localhost:8080/api/prime'; 

  constructor(private http: HttpClient) {}

  onSubmit() {
    this.error = null;
    this.result = null;
    if (this.start !== null && this.end !== null) {
      if (this.start < 1 || this.end < 1) {
        this.error = 'Start and end numbers must be greater than 0';
      } else if (this.start >= this.end) {
        this.error = 'Start number must be less than the end number';
      } else {
        this.getNumbersInRange(this.start, this.end).subscribe({
          next: (response) => this.result = response.join(', '),
          error: (error) => console.error('There was an error!', error)
        });
      }
    }
  }

  getNumbersInRange(start: number, end: number): Observable<number[]> {
    const body = { start, end };
    return this.http.post<number[]>(this.apiUrl, body);
  }
}
