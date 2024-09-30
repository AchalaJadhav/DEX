import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiCallService {

  private apiUrl = 'http://localhost:8086/api/v1'
  private tokenUrl = 'http://localhost:8180/realms/noskript/protocol/openid-connect/token';
  private createClientRunUrl = 'http://localhost:9090/scriptless/rest/runner/createclientrunnew.action';
  private api = 'http://localhost:8086/getppt';

  constructor(private http: HttpClient, private oauthService: OAuthService) { }

  // sendWebsiteData(data: any): Observable<any>{
  //   const headers = 
  //   { 
  //     'Authorization': `Bearer ${this.oauthService.getAccessToken()}`
  //   };
  //   return this.http.post<any>(`${this.apiUrl}/initBrowserWithUrl`,data, {headers});
  // }

  /*With Scriptless Auth*/
  sendWebsiteData(accessToken: string, data: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${accessToken}`
    });  
    return this.http.post(`${this.apiUrl}/initBrowserWithUrl`,data, { headers });
  }

  scanWebsiteData(accessToken: string, data: any): Observable<any>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${accessToken}`
    });  
    return this.http.post<any>(`${this.apiUrl}/scanPage`,data, { headers });
  }

  login(username: string, password: string): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    });

    const body = new URLSearchParams();
    body.set('client_id', 'noskript');
    body.set('grant_type', 'password');
    body.set('scope', 'openid');
    body.set('username', username);
    body.set('password', password);

    return this.http.post(this.tokenUrl, body.toString(), { headers });
  }

  
  createClientRun(accessToken: string, clientRunData: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${accessToken}`
    });

    return this.http.post(this.createClientRunUrl, clientRunData, { headers });
  }

  widgetScan(accessToken: string, data: any) {
    const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${accessToken}`
    });
    return this.http.post<any>(`${this.api}`, data, { headers, responseType: 'blob' as 'json' });
  }
}
