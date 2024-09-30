import { Injectable } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { authCodeFlowConfig } from '../config/authCodeFlowConfig';
import { JwksValidationHandler } from 'angular-oauth2-oidc-jwks';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService 
{

  // userName: string = "";
  // constructor(private oauthService: OAuthService)
  // {
  //   this.configureSSO();
  //   const userClaim: any = this.oauthService.getIdentityClaims();
  //   this.userName = userClaim.name ? userClaim.name : "--NoUser--";
    
  //   if(this.userName == null)
  //   {
  //     console.log("From KeycloakService User : null");
  //   }
  //   else
  //   {
  //     console.log("From KeycloakService User : "+this.userName);
  //   }
  // }


  // configureSSO()
  // {
  //   this.oauthService.configure(authCodeFlowConfig)
  //   this.oauthService.tokenValidationHandler = new JwksValidationHandler
  //   this.oauthService.loadDiscoveryDocumentAndTryLogin();
  // }

  // get token()
  // {
  //   let claims: any = this.oauthService.getIdentityClaims();
  //   return claims ? claims: null ;
  // }
  
}
