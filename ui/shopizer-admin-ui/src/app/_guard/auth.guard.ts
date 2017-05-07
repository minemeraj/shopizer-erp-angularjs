/**
 * Auth guard is used to prevent unauthenticated users from accessing restricted routes, 
 * it's used in app.routing.ts to protect the home page route.
 */
import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
 
@Injectable()
export class AuthGuard implements CanActivate {
 
    constructor(private router: Router) { }
 
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        //console.log('Entering can activate');
        if (localStorage.getItem('currentUser')) {
            // logged in so return true
            return true;
        }
 
        // not logged in so redirect to login page with the return url
        //console.log('Going to /login');
        this.router.navigate(['/login'], { queryParams: { returnUrl: state.url }});
        return false;
    }
}