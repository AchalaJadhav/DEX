import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ConfigServiceService {

  private REST_API_SERVER = 'http://localhost:8086/api/v1';
  constructor(private http: HttpClient, private oauthService: OAuthService) {}
  /* tslint:disable:typedef */
  
  scanPages(data: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
  
    // No need to manually pass the accessToken here, as the interceptor will add it
    return this.http.post<any>(`${this.REST_API_SERVER}/scanPage`, data, { headers });
  }
  

  // sendPostRequest(accessToken: string, data: any) {
  //   const headers = new HttpHeaders({
  //     'Content-Type': 'application/json',
  //     'Authorization': `Bearer ${accessToken}`
  //   }); 
  //   return this.http.post<any>(`${this.REST_API_SERVER}/scanCrawlList`,data, { headers });
  // }

  sendPostRequest(restApi: string, data: any) {
    const headers = 
    { 
      // accept: '*/*',
      'content-type': 'application/json', accept: '*/*' ,
      'Authorization': `Bearer ${this.oauthService.getAccessToken()}`
    };
    return this.http.post<any>(restApi, data, {headers});
  }

  launchWithUrlAndScan(data:any): Observable<any>{
    const launchWithUrlAndScanApi = `${this.REST_API_SERVER}`+ '/initBrowserWithUrl';
    console.log("Data: "+data);
    console.log("Value: "+ data.applicationName);
    return this.http.post(launchWithUrlAndScanApi, data);
  }

  initializeAndLaunch(data: any) {
    const headers = { 'content-type': 'application/json', accept: '*/*' };
    const launchWithUrlAndScanApi = `${this.REST_API_SERVER}`+ '/initializeAndLaunch';
    return this.http.post<any>(launchWithUrlAndScanApi, data, {headers});
  }

  closeBrowser(accessToken: string, data: any) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${accessToken}`
    }); 
    return this.http.post<any>(`${this.REST_API_SERVER}/stop`,data, { headers });
  }

  // closeBrowser(data: any) {
  //   const headers = new HttpHeaders({
      
  //     // accept: '*/*',
  //     'Authorization': `Bearer ${this.oauthService.getAccessToken()}`
  //   }); 
  //   return this.http.post<any>(`${this.REST_API_SERVER}/stop`,data, { headers });
  // }
}
