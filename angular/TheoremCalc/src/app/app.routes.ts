import { Route } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { PrimeComponent } from './prime/prime.component';
import { AboutComponent } from './about/about.component';
import { ParserComponent } from './parser/parser.component';

export const routes: Route[] = [
    { path: '', redirectTo: 'home', pathMatch: 'full' },
    { path: 'home', component: HomeComponent },
    { path: 'about', component: AboutComponent },
    { path: 'prime', component: PrimeComponent },
    { path: 'parser', component: ParserComponent},
    { path: '**', redirectTo: 'home' }
];