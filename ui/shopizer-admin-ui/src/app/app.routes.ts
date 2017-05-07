import { Routes } from '@angular/router';

import { LoginRoutes } from './login/login.routes';
import { SignupRoutes } from './signup/signup.routes';
import { DashboardRoutes } from './dashboard/dashboard.routes';

import { DashboardComponent } from './dashboard/index';
import { LoginComponent } from './login/index';
import { AuthGuard } from './_guard/index';

/**
export const routes: Routes = [
  ...LoginRoutes,
  ...SignupRoutes,
  ...DashboardRoutes,
  { path: '**', component: LoginComponent }
];
 * **/

/**
const appRoutes: Routes = [
    { path: '', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
 
    // otherwise redirect to home
    { path: '**', redirectTo: '' }
];**/



export const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  ...LoginRoutes,
  ...SignupRoutes,
  ...DashboardRoutes,
   { path: '**', redirectTo: 'dashboard' }
];

