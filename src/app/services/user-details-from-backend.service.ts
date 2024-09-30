import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserDetailsFromBackendService {
  base: any ='http://localhost:8086';
  constructor(private http: HttpClient, private oauthService: OAuthService) {}

  getHelloMessage(): Observable<any> {
    const headers = 
    { 
      // accept: '*/*',
      'Authorization': `Bearer ${this.oauthService.getAccessToken()}`
    };
    return this.http.get<any>(this.base+'/hello', {headers});
  }
}