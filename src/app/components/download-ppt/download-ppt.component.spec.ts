import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DownloadPptComponent } from './download-ppt.component';

describe('DownloadPptComponent', () => {
  let component: DownloadPptComponent;
  let fixture: ComponentFixture<DownloadPptComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DownloadPptComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DownloadPptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
