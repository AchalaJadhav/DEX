import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { OAuthService } from 'angular-oauth2-oidc';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
 
 constructor(private oauthService: OAuthService, private route: Router)
 {

 }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      var hasValidIdToken = this.oauthService.hasValidIdToken();
      var hasValidAccessToken = this.oauthService.hasValidAccessToken();

    if(hasValidIdToken && hasValidAccessToken)
    {
      console.log('canActivate t');
      return true;
    }
    else
    {
      console.log('canActivate f');
      //this.route.navigate(["/welcome"]);
      return false;
    }
      
  }
  
}
