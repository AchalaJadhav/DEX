import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CrawlMultiplePagesService {

  crData : any
  private apiUrl = 'http://localhost:8086/api/v1/crawl';
  jsonObject: any;
  private crawledUrls!: any[];

  constructor(private http: HttpClient, private oauthService: OAuthService) {}

  startCrawling(accessToken: string, data: any): Observable<any> {
    this.jsonObject = {
      baseUrl: data
    };
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${accessToken}`
    }); 
    const crawlUrl = `${this.apiUrl}`;
    return this.http.post(crawlUrl,  this.jsonObject, {headers});
  }

  sendSelectedRows(accessToken: string, data: any): Observable<any> {
    const sendSelectedRowsUrl = 'http://localhost:8086/api/v1/scanUrlList';
    console.log("Data: "+data);
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${accessToken}`
    }); 
    return this.http.post(sendSelectedRowsUrl, data, {headers});
  }

  getCrawledUrls(): any[] {
    return this.crawledUrls;
  }

  setCrawledUrls(urls: any[]): void {
    this.crawledUrls = urls;
  }

  
}
