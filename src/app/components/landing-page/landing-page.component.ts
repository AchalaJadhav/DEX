import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Location } from '@angular/common';
import { ApiCallService } from 'src/app/services/dex/api-call.service';
import { ConfigServiceService } from 'src/app/services/dex/config-service.service';
import { SharedDataService } from 'src/app/services/dex/shared-data.service';
import { CrawlMultiplePagesService } from 'src/app/services/dex/crawl-multiple-pages.service';
import { MatDialog } from '@angular/material/dialog';
import { UserDetailsFromBackendService } from 'src/app/services/user-details-from-backend.service';
import { OAuthService } from 'angular-oauth2-oidc';
import { PlayStoreSentimentDialogComponent } from '../sa/play-store-sentiment-dialog/play-store-sentiment-dialog.component';
import { AppStoreSentimentDialogComponent } from '../sa/app-store-sentiment-dialog/app-store-sentiment-dialog.component';
import { FormDataService } from 'src/app/services/form-data.service';
import { HttpClient } from '@angular/common/http';
import { DownloadPptComponent } from '../download-ppt/download-ppt.component';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent implements OnInit {

  websiteDetailsForm!: FormGroup;
  schedulingForm!: FormGroup;
  scanPageResult: any;
  initializeFlag = false;
  body: any;
  jsonObject: any;
  public scanStarted = false;
  dashboardEnabled: boolean = false;
  DashboardVisible: boolean = false;
  closeBrowserResult: any;
  crawledUrls: { url: string; title: string; selected: boolean }[] = [];
  @ViewChild('myDiv')
  myDiv!: ElementRef;
  isCheckboxChecked = false;
  isCheckedEmail: any;
  isCheckedSchdule: any;
  isCheckedSA: any;
  backgroundColor = '#016E73'; // Initial background color
  dynamicHeight = '100vh'; // Initial height
  showNewDiv = false; // Flag to show/hide new div
  userName: string = "";
  helloText: string = "";
  formData: any;
  scheduleType = 'Daily';
  daysInMonth: number[] = Array.from({length: 31}, (_, i) => i + 1);



  constructor(private fb: FormBuilder, private apiService: ApiCallService, private router: Router, private spinner: NgxSpinnerService, 
              private toastr: ToastrService, private sharedDataService: SharedDataService, public configService: ConfigServiceService, 
              private crawlService: CrawlMultiplePagesService, private location: Location, public dialog: MatDialog,
              private oauthService: OAuthService, private userDetailsFromBackendService: UserDetailsFromBackendService,
              private formDataService: FormDataService, private http: HttpClient){}

  ngOnInit(): void {
    this.websiteDetailsForm = this.fb.group({
        applicationName: ['', Validators.required],
        buildId: ['1', Validators.required],
        url: ['', Validators.required],
        pageTitle: ['', Validators.required],
        scans: 1,
        deviceType: 'Desktop',
      }
    );

    this.schedulingForm = this.fb.group({
      schedule: ['', Validators.required],
      selectTime: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required]
    });

    // this.disableAllFieldsExcept(['pageTitle']);
    // this.disableAllFieldsExcept(['pageTitle', 'url']);
    // this.disableAllFieldsExcept('pageTitle');

    // this.scanStarted = true;

    //Login//
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

  // startBrowser() {
  //   this.toastr.info('Browser starting in background, please wait till webpage is loading, if not load it manually.', 'Information', { timeOut: 5000 });

  //   this.spinner.show();
  //   this.apiService.sendWebsiteData(this.websiteDetailsForm.value).subscribe(
  //     res => {
  //       // this.toastr.info('Please enter your Page Title of webpage which is loaded in browser.', 'Information', { timeOut: 8000 });
  //       this.spinner.hide();
  //       this.scanPageResult = res.responseMessage;
  //       console.log("In Initialize" + res);
  //       this.startScan();
  //       // Disable all fields except pageTitle
  //       this.disableAllFieldsExcept(['pageTitle']);
  //       // Set the variable to true when the scan starts and changes to scan multiple pages
  //       this.scanStarted = true;
  //     },
  //     err => {
  //       this.spinner.hide();
  //       this.toastr.error('Error');
  //       this.scanPageResult = err.status;
  //       this.initializeFlag = true;
  //     }
  //   );
  //   setTimeout(() => {
  //     ( err: { status: any; }) => {
  //       this.spinner.hide();
  //       this.toastr.error('Error');
  //       this.scanPageResult = err.status;
  //       this.initializeFlag = true;
  //     }
  //   }, 30000);
  // }

  // startScan() {
  //   this.sharedDataService.myMap.set('scans', this.sharedDataService.myMap.get('scans') + 1);
  //   this.jsonObject = {
  //     applicationName: this.websiteDetailsForm.value.applicationName,
  //     buildId: this.websiteDetailsForm.value.buildId,
  //     deviceType: this.websiteDetailsForm.value.deviceType,
  //     pageTitle: this.websiteDetailsForm.value.pageTitle,
  //     scans: this.sharedDataService.myMap.get('scans')
  //   };
  //   this.sharedDataService.myMap.set('applicationName', this.websiteDetailsForm.value.applicationName),
  //   this.sharedDataService.myMap.set('buildId', this.websiteDetailsForm.value.buildId)
  //   this.spinner.show();
  //   this.toastr.info('Analyzing your webpage!');
  //   this.configService.sendPostRequest('http://' + 'localhost' + ':'+ '8086' + '/api/v1/scanPage', this.jsonObject).subscribe(
  //     res => {
  //       this.spinner.hide();
  //       console.log(res);
  //       this.toastr.info('Analysis done successfully, click on View Dashboard for visualization. You can scan different pages if needed, else close the browser', 'Information', { timeOut: 8000 });
  //       this.scanPageResult = res.responseMessage;
  //     },
  //     err => {
  //       this.spinner.hide();
  //       this.toastr.error('Error');
  //       this.scanPageResult = err.status;
  //       this.initializeFlag = true;
  //     }
  //   );
  //   setTimeout(() => {
  //     this.toastr.info('Auditing is complete, Please rest back for a minute for data ingestion.');
  //     ( err: { status: any; }) => {
  //       this.spinner.hide();
  //       this.toastr.error('Error');
  //       this.scanPageResult = err.status;
  //       this.initializeFlag = true;
  //     }
  //   }, 40000);
  // }

  // scanMultiplePage() {
  //   console.log("In Multiple Method")
  //   this.sharedDataService.myMap.set('scans', this.sharedDataService.myMap.get('scans') + 1);
  //   this.jsonObject = {
  //     applicationName: this.sharedDataService.myMap.get('applicationName'),
  //     buildId: this.sharedDataService.myMap.get('buildId'),
  //     deviceType: this.websiteDetailsForm.value.deviceType,
  //     pageTitle: this.websiteDetailsForm.value.pageTitle,
  //     scans: this.sharedDataService.myMap.get('scans')
  //   };
  //   this.spinner.show();
  //   this.toastr.info('Analyzing your webpage!');
  //   this.configService.sendPostRequest('http://' + 'localhost' + ':'+ '8086' + '/api/v1/scanPage', this.jsonObject).subscribe(
  //     res => {
  //       this.spinner.hide();
  //       console.log(res);
  //       this.toastr.info('Analysis done successfully, click on View Dashboard for visualization. You can scan different pages if needed, else close the browser', 'Information', { timeOut: 8000 });
  //       this.scanPageResult = res.responseMessage;
  //     },
  //     err => {
  //       this.spinner.hide();
  //       this.toastr.error('Error');
  //       this.scanPageResult = err.status;
  //       this.initializeFlag = true;
  //     }
  //   );
  //   setTimeout(() => {
  //     this.toastr.info('Auditing is complete, Please rest back for a minute for data ingestion.');
  //     ( err: { status: any; }) => {
  //       this.spinner.hide();
  //       this.toastr.error('Error');
  //       this.scanPageResult = err.status;
  //       this.initializeFlag = true;
  //     }
  //   }, 40000);
  // }
  
  disableAllFieldsExcept(enableFields: string[]): void {
    Object.keys(this.websiteDetailsForm.controls).forEach((controlName) => {
      const control = this.websiteDetailsForm.get(controlName);
      const formControlElement = document.getElementById(controlName);
  
      if (control && formControlElement) {
        if (enableFields.includes(controlName)) {
          control.enable();
          formControlElement.classList.remove('disabled-field');
          formControlElement.removeAttribute('title');
        } else {
          control.disable();
          control.markAsUntouched();
          control.setErrors(null);
          control.updateValueAndValidity();
  
          formControlElement.classList.add('disabled-field');
          formControlElement.setAttribute('title', '');
        }
      }
    });
  }
  

  isFieldDisabled(fieldName: string): boolean {
    const control = this.websiteDetailsForm.get(fieldName);
    return control ? !control.enabled : false;
  }

  enableDashboard() {
    this.dashboardEnabled = true;
  }

  showDashboard() {
    // this.DashboardVisible = !this.DashboardVisible;  
    window.location.href = 'http://localhost:8084/dashboards', '_blank';
  }
  
  // closeBrowser(): void{
  //   this.configService.sendPostRequest('http://' + 'localhost' + ':'+ '8086' + '/api/v1/stop', '').subscribe(
  //     res => {
  //       this.toastr.info('Successfully stopped browser');
  //       this.closeBrowserResult = res.responseMessage;
  //       // Reload the page to the default landing page
  //       this.location.go('/'); // Use 'go' method to navigate to the specified URL
  //       this.location.replaceState('/'); // Replace the current state with the new URL
  //       window.location.reload(); // Reload the page
  //     },
  //     err => {
  //       this.closeBrowserResult = err.status;
  //     }
  //   );
  // }
  
  // startCrawling() {
  //   this.spinner.show();
  //   this.toastr.info('Crawling your webpage!');
  //   this.sharedDataService.myMap.set('scans', this.sharedDataService.myMap.get('scans') + 1);
  //   this.sharedDataService.myMap.set('applicationName', this.websiteDetailsForm.value.applicationName);
  //   this.sharedDataService.myMap.set('buildId', this.websiteDetailsForm.value.buildId);
  //   this.sharedDataService.myMap.set('deviceType', this.websiteDetailsForm.value.deviceType);
  //   this.crawlService.startCrawling(this.websiteDetailsForm.value.url).subscribe(
  //     (response) => { // next() callback: receives the data and stores it
  //       this.spinner.hide();        
  //       console.log(response);

  //       if (typeof response === 'object') {
  //         // Map the object keys to an array of objects
  //         this.crawledUrls = Object.keys(response).map((url) => ({
  //           url,
  //           title: response[url],
  //           selected: false,
  //         }));
  //         // Set the crawled URLs in the service
  //         this.crawlService.setCrawledUrls(this.crawledUrls);
  //         const url = this.websiteDetailsForm.value.url;
  //         this.sharedDataService.setUrl(url);
  //         this.router.navigate(['/crawl']);
  //       } 
  //       else {
  //         this.spinner.hide();
  //         this.toastr.error('Invalid response format: ', response); 
  //       }
  //     },
  //     (error) => { // error() callback: handle error
  //       this.spinner.hide();
  //       this.toastr.error('Error: ', error); 
  //     },
  //     () => { // complete() callback: handle completion (optional)
  //       this.spinner.hide();
  //       this.toastr.info('Crawled URLs successfully.'); 
  //     }
  //   );
  // }

  toggleButton(checked: boolean): void {
    this.isCheckboxChecked = checked;
    if (checked) {
      this.myDiv.nativeElement.style.height = 'auto';
    } else {
      this.myDiv.nativeElement.style.height = '100vh';
    }
  }

  addNewDiv() {
    this.showNewDiv = true;
    this.dynamicHeight = '125vh'; // Update height when new div is added
  }

  onSubmit() {
    if (this.schedulingForm.valid) {
        // Process form data here
        console.log(this.schedulingForm.value);
    } else {
        // Handle form validation errors
    }
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

  openDialog(): void {
    this.dialog.open(PlayStoreSentimentDialogComponent, {
      width: '892px'
    });
  }
  
  openAppleDialog(): void {
    this.dialog.open(AppStoreSentimentDialogComponent, {
      width: '892px'
    });
  }

  openDownloadDialog(): void {
    this.dialog.open(DownloadPptComponent, {
      width: '892px'
    });
  }

  //Scriptless Api Calls

  onSubmitClick(){
    this.formData = this.formDataService.getData();
    // Or use JSON.stringify to log it as a string
    
    const clientRunData = {
      clientPort: "0",
      defectLogEnabled: "false",
      emailFormat: "",
      emailTriggerEnabled: "false",
      endIndex: "0",
      executionMode: "Parallel",
      executionTriggerFrom: "NoSkript",
      framework: "Noskript",
      notRestrictdx: null,
      projectId: "",
      roiStatus: "false",
      runAction: "true",
      runId: "",
      selfHealEnabled: "false",
      serverPort: 0,
      skipMavenInstallation: "false",
      startIndex: 0,
      taskId: null,
      tcmToolEnabled: "false",
      threadCount: 1,
      userExecutionToken: "fedacbf3-8476-40de-b1b5-40a9e49e152b",
      dxreportvo: {
        dxaccessbility: false,
        dxclintesideperformance: false,
        dxsentimentalanalysis: true,
        dxemailreport: true,
        dxscheduler: true,
        url: "",
        dxdomain: "",
        email: "Achala.Jadhav@ust.com",
        scheduleType: "Daily",
        scheduleTime: "10:00 AM",
        endDate: "2024-06-27T18:30:00.000Z",
        dxapplestoreconfiguration: {
          appleId: this.formData.appleId,
          userName: this.formData.userName,
          countryCode: this.formData.countryCode,
          languageCode: this.formData.languageCode,
          deviceName: this.formData.device,
          password: this.formData.password,
          Id: "",
          userId: ""
        },
        scheduleEndDate: "06/28/2024",
        requestid: "",
        userId: "",
        accessbilityUrlIds: []
      },
      dxreport: "true",
      freeTierLimit: true,
      accessibilityAllLinks: null
    };
    
    console.log("From Landing Page: " + JSON.stringify(this.formData, null, 2)); 
    this.apiService.login('admin', 'admin#123').subscribe(
      response => {
        const accessToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJzNmU5QXpyd1BoN2FOYWlCQ2kyYWFkd2ljMENFZTFLLTBhU05PczdkenpZIn0.eyJleHAiOjE3MTg5MzQ5NzQsImlhdCI6MTcxODkzNDY3NCwiYXV0aF90aW1lIjoxNzE4OTM0Mzc3LCJqdGkiOiIxNGY5NWFjMC0xZjEzLTRkZTYtOGZiNS0zYTc1ZjRlYTA2MjUiLCJpc3MiOiJodHRwczovL2F1dGgudXN0LXFlLXBsYXRmb3JtLmNvbS9yZWFsbXMvY2hlIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImZlZGFjYmYzLTg0NzYtNDBkZS1iMWI1LTQwYTllNDllMTUyYiIsInR5cCI6IkJlYXJlciIsImF6cCI6Im5vc2tyaXB0Iiwibm9uY2UiOiJmNGVjMjQ2Mi1hZWRiLTRkMmUtOGJmOS02NWUwZDg2OWUyNzEiLCJzZXNzaW9uX3N0YXRlIjoiZmQ2YzdlYzgtYjM3OS00ZTI5LTk5NDAtM2YzMTliNDJiZWQ4IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJEZXZlbG9wZXIiLCJkZWZhdWx0LXJvbGVzLWNoZSJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIHByb2ZpbGUgZW1haWwiLCJzaWQiOiJmZDZjN2VjOC1iMzc5LTRlMjktOTk0MC0zZjMxOWI0MmJlZDgiLCJUaW1lem9uZSI6IjM0IiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJyb2xlIjpbIkRldmVsb3BlciIsImRlZmF1bHQtcm9sZXMtY2hlIl0sIlBsYXRmb3JtIjoiTm9za3JpcHQiLCJMaWNlbmNlRXhwaXJ5RGF0ZSI6IjIwMjUtMTItMzEiLCJuYW1lIjoiQWNoYWxhIEphZGhhdiBBY2hhbGEgSmFkaGF2IiwicHJlZmVycmVkX3VzZXJuYW1lIjoiYWNoYWxhIiwiTGljZW5jZVN0YXJ0RGF0ZSI6IjIwMjAtMDEtMDEiLCJnaXZlbl9uYW1lIjoiQWNoYWxhIEphZGhhdiIsImZhbWlseV9uYW1lIjoiQWNoYWxhIEphZGhhdiIsImVtYWlsIjoiYWNoYWxhLmphZGhhdkB1c3QuY29tIn0.Jc6CPKUIeI2jbeqzT6KzEJiApBfebe5IDAxbxELXG7KM_ShdgOCeEpawDhJ6w5CoJelm4lm_qwrgETakeDUiNY3cNiWy_x9JQJ0PN1Lh6f7REo8HX5dHnMVg11RhNfDzMGOINix_4aZmFLM_7YHohfayvtkv6z3eGNtJpEJ67aOVbjIeb29I8FpB-aiJ_2gqj8NKo6MgAp5LQQHaxIcgD9aWC4sAGfgkoEODMW5w3bsupk81x0AV8wxD6irxnljsO1cpymA0neCwnUuiVA0KEi8yfqZ-hr6iD1GkHhR4wGE5Tfim4Mj_qT2-Q0DT5fyeKZIO8DhTGSRpN1s_H8nVUw";
        console.log('Access token received:', accessToken);

        this.apiService.createClientRun(accessToken, clientRunData).subscribe(
          createResponse => {
            console.log('Client run created successfully:', createResponse);
          },
          createError => {
            console.error('Error creating client run:', createError);
          }
        );
      },
      error => {
        console.error('Error obtaining access token:', error);
      }
    );
  }

  //With Nosckript Auth

  startBrowser() {
    this.toastr.info('Browser starting in background, please wait till webpage is loading, if not load it manually.', 'Information', { timeOut: 5000 });

    this.spinner.show();

    // this.apiService.loginCred().subscribe(
    //   response => {
    //     const accessToken = response.access_token;
    //     console.log('Access token received:', accessToken);

        this.apiService.sendWebsiteData(this.websiteDetailsForm.value).subscribe(
          res => {
            // this.toastr.info('Please enter your Page Title of webpage which is loaded in browser.', 'Information', { timeOut: 8000 });
            this.spinner.hide();
            this.scanPageResult = res.responseMessage;
            console.log("In Initialize" + res);
            this.startScan();
            // Disable all fields except pageTitle
            this.disableAllFieldsExcept(['pageTitle']);
            // Set the variable to true when the scan starts and changes to scan multiple pages
            this.scanStarted = true;
          },
          err => {
            this.spinner.hide();
            this.toastr.error('Error');
            this.scanPageResult = err.status;
            this.initializeFlag = true;
          }
        );
        setTimeout(() => {
          ( err: { status: any; }) => {
            this.spinner.hide();
            this.toastr.error('Error');
            this.scanPageResult = err.status;
            this.initializeFlag = true;
          }
        }, 30000);
      
  }

  startScan() {
    this.sharedDataService.myMap.set('scans', this.sharedDataService.myMap.get('scans') + 1);
    if (this.websiteDetailsForm.valid) {
      // Start session logic
      this.scanStarted = true;
      this.dashboardEnabled = true;
      // Pass form data to session storage or service as needed
    }
    this.jsonObject = {
      applicationName: this.websiteDetailsForm.value.applicationName,
      buildId: this.websiteDetailsForm.value.buildId,
      deviceType: this.websiteDetailsForm.value.deviceType,
      pageTitle: this.websiteDetailsForm.value.pageTitle,
      scans: this.sharedDataService.myMap.get('scans'),
    };
    this.sharedDataService.myMap.set('applicationName', this.websiteDetailsForm.value.applicationName),
    this.sharedDataService.myMap.set('buildId', this.websiteDetailsForm.value.buildId)
    this.spinner.show();
    this.toastr.info('Analyzing your webpage!');
    
    // this.apiService.loginCred().subscribe(
    //   response => {
    //     const accessToken = response.access_token;
    //     console.log('Access token received:', accessToken);

        this.configService.scanPages(this.jsonObject).subscribe(
          res => {
            this.spinner.hide();
            console.log(res);
            this.toastr.info('Analysis done successfully, click on View Dashboard for visualization. You can scan different pages if needed, else close the browser', 'Information', { timeOut: 8000 });
            this.scanPageResult = res.responseMessage;
          },
          err => {
            this.spinner.hide();
            this.toastr.error('Error');
            this.scanPageResult = err.status;
            this.initializeFlag = true;
          }
        );
        setTimeout(() => {
          this.toastr.info('Auditing is complete, Please rest back for a minute for data ingestion.');
          ( err: { status: any; }) => {
            this.spinner.hide();
            this.toastr.error('Error');
            this.scanPageResult = err.status;
            this.initializeFlag = true;
          }
        }, 40000);
      
  }

  scanMultiplePage() {
    console.log("In Multiple Method")
    this.sharedDataService.myMap.set('scans', this.sharedDataService.myMap.get('scans') + 1);
    this.jsonObject = {
      applicationName: this.sharedDataService.myMap.get('applicationName'),
      buildId: this.sharedDataService.myMap.get('buildId'),
      deviceType: this.websiteDetailsForm.value.deviceType,
      pageTitle: this.websiteDetailsForm.value.pageTitle,
      scans: this.sharedDataService.myMap.get('scans')
    };
    this.spinner.show();
    this.toastr.info('Analyzing your webpage!');

    // this.apiService.loginCred().subscribe(
    //   response => {
    //     const accessToken = response.access_token;
    //     console.log('Access token received:', accessToken);

        this.configService.scanPages(this.jsonObject).subscribe(
          res => {
            this.spinner.hide();
            console.log(res);
            this.toastr.info('Analysis done successfully, click on View Dashboard for visualization. You can scan different pages if needed, else close the browser', 'Information', { timeOut: 8000 });
            this.scanPageResult = res.responseMessage;
          },
          err => {
            this.spinner.hide();
            this.toastr.error('Error');
            this.scanPageResult = err.status;
            this.initializeFlag = true;
          }
        );
        setTimeout(() => {
          this.toastr.info('Auditing is complete, Please rest back for a minute for data ingestion.');
          ( err: { status: any; }) => {
            this.spinner.hide();
            this.toastr.error('Error');
            this.scanPageResult = err.status;
            this.initializeFlag = true;
          }
        }, 40000);
  }

  startCrawling() {
    this.spinner.show();
    this.toastr.info('Crawling your webpage!');
    this.sharedDataService.myMap.set('scans', this.sharedDataService.myMap.get('scans') + 1);
    this.sharedDataService.myMap.set('applicationName', this.websiteDetailsForm.value.applicationName);
    this.sharedDataService.myMap.set('buildId', this.websiteDetailsForm.value.buildId);
    this.sharedDataService.myMap.set('deviceType', this.websiteDetailsForm.value.deviceType);
    
    this.apiService.loginCred().subscribe(
      response => {
        const accessToken = response.access_token;
        console.log('Access token received:', accessToken);

        this.crawlService.startCrawling(accessToken, this.websiteDetailsForm.value.url).subscribe(
          (response) => { // next() callback: receives the data and stores it
            this.spinner.hide();        
            console.log(response);

            if (typeof response === 'object') {
              // Map the object keys to an array of objects
              this.crawledUrls = Object.keys(response).map((url) => ({
                url,
                title: response[url],
                selected: false,
              }));
              // Set the crawled URLs in the service
              this.crawlService.setCrawledUrls(this.crawledUrls);
              const url = this.websiteDetailsForm.value.url;
              this.sharedDataService.setUrl(url);
              this.router.navigate(['/crawl']);
            } 
            else {
              this.spinner.hide();
              this.toastr.error('Invalid response format: ', response); 
            }
          },
          (error) => { // error() callback: handle error
            this.spinner.hide();
            this.toastr.error('Error: ', error); 
          },
          () => { // complete() callback: handle completion (optional)
            this.spinner.hide();
            this.toastr.info('Crawled URLs successfully.'); 
          }
        );
      }
    );
  }

  closeBrowser(): void{
    this.apiService.loginCred().subscribe(
      response => {
        const accessToken = response.access_token;
        console.log('Access token received:', accessToken);

        this.configService.closeBrowser(accessToken, '').subscribe(
          res => {
            this.toastr.info('Successfully stopped browser');
            this.closeBrowserResult = res.responseMessage;
            // Reload the page to the default landing page
            this.location.go('/landing'); // Use 'go' method to navigate to the specified URL
            this.location.replaceState('/landing'); // Replace the current state with the new URL
            window.location.reload(); // Reload the page
          },
          err => {
            this.closeBrowserResult = err.status;
          }
        );
      }
    );
  }


  onDownloadReport() {
    if (this.scanStarted) {
      const applicationName = this.sharedDataService.myMap.get('applicationName');
      const buildId = this.sharedDataService.myMap.get('buildId');
      // Pass applicationName and buildId to the download report function
      this.downloadReport(applicationName, buildId);
    } else {
      this.openDownloadDialog();
    }
  }


  downloadReport(applicationName: string, buildId: string) {
    const body = {
      applicationName: applicationName,
      buildId: buildId
    };
    this.toastr.info('Downloading the report.', 'Information', { timeOut: 2000 });

    this.spinner.show();

    this.apiService.loginCred().subscribe(
        response => {
            const accessToken = response.access_token;
            console.log('Access token received:', accessToken);

            this.apiService.widgetScan(accessToken, body).subscribe(
                (response) => {
                    this.spinner.hide();
                    this.toastr.info('Downloaded the report, check in reports folder.', 'Information', { timeOut: 8000 });

                    const blob = new Blob([response], { type: 'application/octet-stream' });
                    const url = window.URL.createObjectURL(blob);
                    window.open(url);
                },
                (error) => {
                    this.spinner.hide();
                    this.toastr.error('Error');
                    console.error('Error downloading PPT:', error);
                }
            );
        },
        error => {
            this.spinner.hide();
            this.toastr.error('Error during login');
            console.error('Login error:', error);
        }
    );
  }

  
}