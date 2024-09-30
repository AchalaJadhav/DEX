import { Component, NgModule } from '@angular/core';
import { Router, RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { AuthGuard } from './utils/auth.guard';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { CrawlUrlComponent } from './components/crawl-url/crawl-url.component';

const routes: Routes = 
[

  {path : "welcome" ,component: WelcomeComponent},
  {path : "" , component: WelcomeComponent, pathMatch:"full"},
  { path: 'landing', component: LandingPageComponent },
  { path: 'crawl', component: CrawlUrlComponent, pathMatch: 'full' },
  //{path : "**" , component: WelcomeComponent, pathMatch:"full"},

  {path : "home" , component: HomeComponent, canActivate:[AuthGuard]},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule 
{
}

