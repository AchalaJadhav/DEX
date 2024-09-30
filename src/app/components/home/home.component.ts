import { Component, OnInit } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { JwksValidationHandler } from 'angular-oauth2-oidc-jwks';
import { authCodeFlowConfig } from 'src/app/config/authCodeFlowConfig';
import { UserDetailsFromBackendService } from 'src/app/services/user-details-from-backend.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  userName: string = "";
  helloText: string = "";
  constructor(private oauthService: OAuthService, private userDetailsFromBackendService: UserDetailsFromBackendService) { }

  ngOnInit(): void {
    const userClaim: any = this.oauthService.getIdentityClaims();
    
    if(userClaim)
    {
      this.userName = userClaim.name ? userClaim.name : "--NoUser--";
      if(this.userName == null)
      {
        console.log("User : null");
      }
      else
      {
        console.log("User : "+this.userName);
      }
    }
    else
    {
      console.log('no userclaim found');
    }

      
    
  }


  ////////////////

  getHelloText() {
    console.log("func1");
    this.userDetailsFromBackendService.getHelloMessage()
        .subscribe(response => {
          if (typeof response === 'object') {
            console.log("inside if first");
            console.log(response);
            console.log(response.message);
            console.log(this.oauthService.getAccessToken());
            this.helloText = response.message;
            console.log("HelloText: " + this.helloText);
          }
    }    
    );
  }

  logout()
  {
    console.log("logout");
    this.oauthService.logOut();
  }

  get token()
  {
    let claims: any = this.oauthService.getIdentityClaims();
    return claims ? claims: null ;
  }

}
