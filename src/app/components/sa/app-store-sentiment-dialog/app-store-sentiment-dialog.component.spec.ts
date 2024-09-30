import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppStoreSentimentDialogComponent } from './app-store-sentiment-dialog.component';

describe('AppStoreSentimentDialogComponent', () => {
  let component: AppStoreSentimentDialogComponent;
  let fixture: ComponentFixture<AppStoreSentimentDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppStoreSentimentDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppStoreSentimentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
