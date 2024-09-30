import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { FormDataService } from 'src/app/services/form-data.service';

@Component({
  selector: 'app-app-store-sentiment-dialog',
  templateUrl: './app-store-sentiment-dialog.component.html',
  styleUrls: ['./app-store-sentiment-dialog.component.css']
})
export class AppStoreSentimentDialogComponent implements OnInit {
  form!: FormGroup;

  constructor(private fb: FormBuilder, private formDataService: FormDataService,  private toastr: ToastrService,
    private dialogRef: MatDialogRef<AppStoreSentimentDialogComponent>) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      appleId: ['', Validators.required],
      userName: ['', Validators.required],
      countryCode: ['', Validators.required],
      languageCode: ['', Validators.required],
      device: ['', Validators.required],
      password: ['', Validators.required]
    });
  }
  
  onSaveClick(): void {
    this.toastr.info('Saved your data.', 'Information', { timeOut: 5000 });

    console.log('Save button clicked');
    console.log('From PlayStore Data:', this.form.value); // Log form data to the console
    this.formDataService.setData(this.form.value);

    // Close the dialog box
    this.dialogRef.close();
  }

}
