import { AuthConfig } from 'angular-oauth2-oidc';

  export const authCodeFlowConfig: AuthConfig = 

  // {
  //   issuer: 'http://localhost:8180/realms/my-test-realm',
  //   tokenEndpoint: 'http://localhost:8180/realms/my-test-realm/protocol/openid-connect/token',
  //   redirectUri: window.location.origin,
  //   clientId: 'my-webapp-client',
  //   responseType: 'code',
  //   scope: 'openid profile',
  //   showDebugInformation: true,
  // };

  {
    issuer: "http://localhost:8180/realms/noskript",
    tokenEndpoint: 'http://localhost:8180/realms/noskript/protocol/openid-connect/token',
    redirectUri:window.location.origin,
    clientId:'noskript',
    responseType:'code',
    scope: 'openid profile',
    showDebugInformation: true,
  };
  
  
  
  
  
  
  
  
  
  // issuer: 'http://localhost:8180/realms/oauth2-demo-realm',
  // redirectUri:window.location.origin,
  // clientId:'oauth2-demo-pkce-client',
  // responseType:'code',
  // strictDiscoveryDocumentValidation: true,
  // scope: 'openid profile email offline_access'
  
  
  
  // {
  //   // Url of the Identity Provider
  //   issuer: 'https://idsvr4.azurewebsites.net',

  //   // URL of the SPA to redirect the user to after login
  //   redirectUri: window.location.origin + '/index.html',

  //   // The SPA's id. The SPA is registerd with this id at the auth-server
  //   // clientId: 'server.code',
  //   clientId: 'spa',

  //   // Just needed if your auth server demands a secret. In general, this
  //   // is a sign that the auth server is not configured with SPAs in mind
  //   // and it might not enforce further best practices vital for security
  //   // such applications.
  //   // dummyClientSecret: 'secret',

  //   responseType: 'code',

  //   // set the scope for the permissions the client should request
  //   // The first four are defined by OIDC.
  //   // Important: Request offline_access to get a refresh token
  //   // The api scope is a usecase specific one
  //   scope: 'openid profile email offline_access api',

  //   showDebugInformation: true,
  // };