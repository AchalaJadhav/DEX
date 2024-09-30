import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlayStoreSentimentDialogComponent } from './play-store-sentiment-dialog.component';

describe('PlayStoreSentimentDialogComponent', () => {
  let component: PlayStoreSentimentDialogComponent;
  let fixture: ComponentFixture<PlayStoreSentimentDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PlayStoreSentimentDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlayStoreSentimentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
