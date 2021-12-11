import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard implements CanActivate {
  
  constructor(private router: Router) {}
  
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
  
      if (state.url == "/login") {
        return true;
      }

      let token = sessionStorage.getItem('token');

      if (!token) {
        return this.router.parseUrl('/login');
      } 

      return true;
  }
  
}
