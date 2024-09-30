import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Location } from '@angular/common';
import { CrawlMultiplePagesService } from 'src/app/services/dex/crawl-multiple-pages.service';
import { SharedDataService } from 'src/app/services/dex/shared-data.service';
import { ConfigServiceService } from 'src/app/services/dex/config-service.service';
import { ApiCallService } from 'src/app/services/dex/api-call.service';

interface UrlWithTitle {
  url: string;
  title: string;
  selected: boolean;
}

@Component({
  selector: 'app-crawl-url',
  templateUrl: './crawl-url.component.html',
  styleUrls: ['./crawl-url.component.css']
})
export class CrawlUrlComponent implements OnInit {

  crawledUrls: any[] = [];
  url: any;
  currentPage: number = 1;
  itemsPerPage: number = 5;
  applicationName: any;
  buildId: any;
  deviceType: any;
  pageTitle: any;
  uniquePageTitles: string[] = [];
  selectedUrls: any[] = [];
  scanPageResult: any;
  initializeFlag = false;
  scans: any;
  closeBrowserResult: any;
  dashboardEnabled: boolean = false;
  DashboardVisible: boolean = false;
  
  constructor(private crawlService: CrawlMultiplePagesService, private route: ActivatedRoute, 
              private sharedDataService: SharedDataService, private spinner: NgxSpinnerService, 
              private toastr: ToastrService,  public configService: ConfigServiceService, private router: Router, 
              private location: Location, private apiService: ApiCallService) { }

  ngOnInit(): void {

    //Start Initialise Chromebrowser
    // this.toastr.info('Browser starting in background, please wait till webpage is loading, if not load it manually.', 'Information', { timeOut: 5000 });

    // this.spinner.show();
    // this.configService.initializeAndLaunch('').subscribe(
    //   res => {
    //     // this.toastr.info('Please enter your Page Title of webpage which is loaded in browser.', 'Information', { timeOut: 8000 });
    //     this.spinner.hide();
    //     this.scanPageResult = res.responseMessage;
    //   },
    //   err => {
    //     this.spinner.hide();
    //     this.toastr.error('Error');
    //     this.scanPageResult = err.status;
    //     this.initializeFlag = true;
    //   }
    // );
    // setTimeout(() => {
    //   ( err: { status: any; }) => {
    //     this.spinner.hide();
    //     this.toastr.error('Error');
    //     this.scanPageResult = err.status;
    //     this.initializeFlag = true;
    //   }
    // }, 30000);

    //End Initialise Chromebrowser


    //FuncName
    this.sharedDataService.myMap.set('scans', this.sharedDataService.myMap.get('scans') + 1);

    this.sharedDataService.url$.subscribe((url) => {
      // Use the url as needed
      this.applicationName = this.sharedDataService.myMap.get('applicationName');
      this.buildId = this.sharedDataService.myMap.get('buildId');
      this.deviceType = this.sharedDataService.myMap.get('deviceType');
      this.url = url || '';
      this.scans = this.sharedDataService.myMap.get('scans');
      // this.pageTitle = this.pageTitle || '';
      this.crawledUrls = this.url || []; // Default to an empty string if not provided
    });
    this.crawledUrls = this.crawlService.getCrawledUrls();
  } 

  getCrawlUrl()
  {
    this.sharedDataService.setUrl(this.url);
    this.startCrawling();
    this.crawledUrls = this.crawlService.getCrawledUrls();
  }

  startCrawling() {
    this.spinner.show();
    this.toastr.info('Crawling your webpage!');
    
    this.apiService.login('Achala', 'aaa').subscribe(
      response => {
        const accessToken = response.access_token;
        console.log('Access token received:', accessToken);

        this.crawlService.startCrawling(accessToken, this.url).subscribe(
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
              const url = this.url;
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
  
  getPaginatedUrls(): any[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.crawledUrls.slice(startIndex, endIndex);
  }

  getPageNumbers(): number[] {
    // Check if this.crawledUrls is defined
    if (this.crawledUrls && this.crawledUrls.length > 0) {
      const totalPages = Math.ceil(this.crawledUrls.length / this.itemsPerPage);
      return Array.from({ length: totalPages }, (_, index) => index + 1);
    } else {
      // Return an empty array or handle the case where this.crawledUrls is not defined
      return [];
    }
  }

  changePage(pageNumber: number): void {
    this.currentPage = pageNumber;
  }

  startFunction(): void {
    // Function to trigger with selected URLs and titles
    const selectedUrlsWithTitle = this.selectedUrls.map(url => ({ url: url.url, title: url.title }));
    // Call your desired function with selected URLs and titles
    this.yourFunction(selectedUrlsWithTitle);
  }

  yourFunction(selectedUrlsWithTitle: any[]): void {
    console.log('Selected URLs with titles:', selectedUrlsWithTitle);
  }

  onCheckboxChange(url: any, event: Event): void {
    if (event.target instanceof HTMLInputElement) {
      const isChecked = event.target.checked;
      const existingItem = this.selectedUrls.find(selectedUrl => selectedUrl.url === url.url);

      if (existingItem) {
        existingItem.selected = isChecked;
      } else {
        this.selectedUrls.push({ ...url, selected: isChecked });
      }
    }
  }

  startScansSequentially2(): void {
    // this.sharedDataService.myMap.set('scans', this.sharedDataService.myMap.get('scans') + 1);
    const selectedUrlsWithTitle: UrlWithTitle[] = this.crawledUrls
      .filter((url) => url.selected)
      .map((url) => ({ url: url.url, title: url.title, selected: url.selected }));
      let currentIndex = 0;
      for (let i = 0; i < selectedUrlsWithTitle.length; i++) {
        const currentUrlWithTitle = selectedUrlsWithTitle[i];
      
        // Create the JSON object for the API request
        const jsonObject = {
          applicationName: this.applicationName,
          buildId: this.buildId,
          deviceType: this.deviceType,
          pageTitle: currentUrlWithTitle.title,
          scans: 1,
          url: currentUrlWithTitle.url
        };
          
        if(currentIndex<=2)
        {
          this.ml(jsonObject);
          console.log("currentIndex "+ currentIndex)
          currentIndex++;
        }
        console.log("JSON: "+jsonObject.applicationName,jsonObject.buildId,jsonObject.deviceType,jsonObject.pageTitle,jsonObject.scans,jsonObject.url);
        this.spinner.show();
        this.toastr.info('Analyzing your webpage!');

        setTimeout(() => {
          this.toastr.info('Auditing is complete, Starting ingestion');
          ( err: { status: any; }) => {
            this.spinner.hide();
            this.toastr.error('Error');
            this.scanPageResult = err.status;
            this.initializeFlag = true;
          }
        }, 40000);
      }
  }

   ml(jsonObject:any):void
  {
    
    this.apiService.login('Achala', 'aaa').subscribe(
      response => {
        const accessToken = response.access_token;
        console.log('Access token received:', accessToken);

        this.configService.sendPostRequest(accessToken, jsonObject).subscribe(
          res => {
            this.spinner.hide();
            console.log(res);
            this.toastr.info('Analysis done successfully, click on View Dashboard for visualization. You can scan different pages if needed, else close the browser', 'Information', { timeOut: 8000 });
            this.scanPageResult = res.responseMessage;
            console.log("res.responseMessage :"+res.responseMessage);
            if(res.responseMessage == "Analysis and Ingetion Done Successfully")
            {
              console.log("res.responseMessage1 :"+res.responseMessage);  
              this.router.navigate(['/']);
            }
            else if(res.responseMessage == "Something went wrong during Analysis and Ingestion")
            {
              console.log("res.responseMessage2 :"+res.responseMessage);
            }
          },
          err => {
            this.spinner.hide();
            this.toastr.error('Error');
            this.scanPageResult = err.status;
            this.initializeFlag = true;
          }
        );
      }
    );
  }

  sendSelectedRows() {
    const selectedRows = this.crawledUrls.filter((crawledUrl) => crawledUrl.selected);

    // Extract unique values from selected rows
    const uniqueValues = Array.from(new Set(selectedRows.map(row => row.title)));

    // Format the selected rows for the 'urls' field
    const urls = uniqueValues.reduce((acc, title) => {
      const selectedRow = selectedRows.find(row => row.title === title);
      if (selectedRow && selectedRow.url) {
        acc[title] = selectedRow.url;
      }
      return acc;
    }, {});

    // Construct the payload with the remaining fields
    const payload = {
      applicationName: this.applicationName,
      buildId: this.buildId,
      deviceType: this.deviceType,
      pageTitle: this.pageTitle,
      scans: selectedRows.length,
      urls: urls,
    };

    console.log("Payload to send:", payload);
    this.spinner.show();
    this.toastr.info('Analyzing your webpage!');
    // Uncomment the following code to send the formatted data to the backend

    
    this.apiService.login('Achala', 'aaa').subscribe(
      response => {
        const accessToken = response.access_token;
        console.log('Access token received:', accessToken);

        this.crawlService.sendSelectedRows(accessToken,payload).subscribe(
          res => {
            this.spinner.hide();
            console.log(res);
            this.toastr.info('Analysis done successfully, click on View Dashboard for visualization. You can scan different pages if needed, else close the browser', 'Information', { timeOut: 10000 });
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
          this.toastr.info('Auditing is complete, Starting ingestion.');
          ( err: { status: any; }) => {
            this.spinner.hide();
            this.toastr.error('Error');
            this.scanPageResult = err.status;
            this.initializeFlag = true;
          }
          }, 50000,
        () => { // complete() callback: handle completion (optional)
            this.spinner.hide();
            this.toastr.info('Crawled URLs successfully.'); 
          });
        }
    );
  }

  closeBrowser(): void{
    
    this.apiService.login('Achala', 'aaa').subscribe(
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
  
  enableDashboard() {
    this.dashboardEnabled = true;
  }

  showDashboard() {
    this.DashboardVisible = !this.DashboardVisible;  
  }
}