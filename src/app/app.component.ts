import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'DexAppUI';

  constructor(private keycloakService: KeycloakService) {}
  
  ngOnInit() {
    const keycloakConfig = {
      url: 'https://preprod.keycloak.noskript.com/realms/che/protocol/openid-connect/token',
      realm: 'noskript',
      clientId: 'noskript-public'
    };

    // Initialize Keycloak with the specified configuration
    this.keycloakService.init({
      config: keycloakConfig,
      initOptions: {
        onLoad: 'login-required', // Now this is placed under initOptions
        checkLoginIframe: false, // Disable for simplicity in this example
      }
    }).then(() => {
      console.log('Keycloak initialized');
      return this.keycloakService.getToken(); // Fetch the token here
    }).then(token => {
      console.log('Token:', token);
    }).catch(error => {
      console.error('Error during Keycloak initialization:', error);
    });
  }
}
