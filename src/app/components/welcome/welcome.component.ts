import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OAuthService } from 'angular-oauth2-oidc';
import { authCodeFlowConfig } from 'src/app/config/authCodeFlowConfig';
import { KeycloakService } from 'src/app/services/keycloak.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  constructor(private oauthService: OAuthService,private route: Router) { }

  ngOnInit(): void 
  {
    this.initializeOAuth(this.oauthService);
    if(this.token)
    {
      console.log("WelcomeComponent L");
      this.route.navigate(['home']);
    }
    else
    {
      console.log("WelcomeComponent NL");
    }
  }

  get token()
  {
    let claims: any = this.oauthService.getIdentityClaims();
    return claims ? claims: null ;
  }
  ///////
  login()
  {
    console.log("login");
    this.oauthService.initCodeFlow();
  }

  initializeOAuth(oauthService: OAuthService){
  
    oauthService.configure(authCodeFlowConfig);
    oauthService.setupAutomaticSilentRefresh();
    oauthService.loadDiscoveryDocumentAndLogin();
}

}
