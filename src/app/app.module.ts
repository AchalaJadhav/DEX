import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { HttpClientModule } from '@angular/common/http';
import { OAuthModule } from 'angular-oauth2-oidc';
import { CrawlUrlComponent } from './components/crawl-url/crawl-url.component';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { NgxSpinnerModule } from "ngx-spinner";
import { ToastrModule } from 'ngx-toastr';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatNativeDateModule } from '@angular/material/core';
import {MatDialogModule} from '@angular/material/dialog';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule }   from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MaterialModule } from './material/material.module';
import { MatIconModule } from '@angular/material/icon';
import { PlayStoreSentimentDialogComponent } from './components/sa/play-store-sentiment-dialog/play-store-sentiment-dialog.component';
import { AppStoreSentimentDialogComponent } from './components/sa/app-store-sentiment-dialog/app-store-sentiment-dialog.component';
import { DownloadPptComponent } from './components/download-ppt/download-ppt.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    WelcomeComponent,
    CrawlUrlComponent,
    LandingPageComponent,
    PlayStoreSentimentDialogComponent,
    AppStoreSentimentDialogComponent,
    DownloadPptComponent,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  imports: [
    BrowserModule,
    AppRoutingModule,
    // RouterModule.forRoot([]), // Add your routes inside the forRoot method
    HttpClientModule,
    OAuthModule.forRoot(),
    NgxSpinnerModule.forRoot(),
    ToastrModule.forRoot(),
    MaterialModule,
    NgbModule,
    BrowserAnimationsModule,
    MatSnackBarModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatNativeDateModule,
    MatDialogModule,
    ReactiveFormsModule,
    FormsModule,
    MatIconModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
