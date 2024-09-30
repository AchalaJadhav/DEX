import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable, catchError, from, switchMap, throwError } from 'rxjs';
import { KeycloakService } from 'keycloak-angular';

@Injectable()
export class KeycloakHttpInterceptor implements HttpInterceptor {

  constructor(private keycloakService: KeycloakService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return from(this.keycloakService.getToken()).pipe(
      switchMap(token => {
        const headers = request.headers.set('Authorization', `Bearer ${token}`);
        const clonedRequest = request.clone({ headers });
        return next.handle(clonedRequest);
      }),
      catchError(error => {
        console.error('Error fetching token in interceptor:', error);
        return throwError(error);
      })
    );
  }
  
}
