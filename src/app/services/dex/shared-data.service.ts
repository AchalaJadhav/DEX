import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedDataService {
  
  myMap: Map<string, any> = new Map<string, any>();
  private urlSubject = new BehaviorSubject<string>('');
  public url$ = this.urlSubject.asObservable();
    
  setPageDetails(applicationName: any, buildId: any, deviceType: any, url: any) {
    this.myMap.set('applicationName', applicationName);
    this.myMap.set('buildId', buildId);
    this.myMap.set('deviceType', deviceType);
    this.myMap.set('url', url);
    this.myMap.set('scans', 1);
  }
  
  setUrl(url: string): void {
    this.urlSubject.next(url);
  }
}
