import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { ApiCallService } from 'src/app/services/dex/api-call.service';

@Component({
  selector: 'app-download-ppt',
  templateUrl: './download-ppt.component.html',
  styleUrls: ['./download-ppt.component.css']
})
export class DownloadPptComponent implements OnInit {

  applicationName: any | undefined;
  buildId: any | undefined;

  constructor(private http: HttpClient, private spinner: NgxSpinnerService, 
    private toastr: ToastrService,
    private dialogRef: MatDialogRef<DownloadPptComponent>, private apiService: ApiCallService) { }

  ngOnInit(): void {
  }

  getSnapssss() {
    const body = {
        applicationName: this.applicationName,
        buildId: this.buildId
    };

    this.toastr.info('Downloading the report.', 'Information', { timeOut: 2000 });

    this.spinner.show();

    this.apiService.login('Achala', 'aaa').subscribe(
        response => {
            const accessToken = response.access_token;
            console.log('Access token received:', accessToken);

            this.apiService.widgetScan(accessToken, body).subscribe(
                (response) => {
                    this.spinner.hide();
                    this.toastr.info('Downloaded the report, check in reports folder.', 'Information', { timeOut: 8000 });

                    // Close the dialog box
                    this.dialogRef.close();

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
