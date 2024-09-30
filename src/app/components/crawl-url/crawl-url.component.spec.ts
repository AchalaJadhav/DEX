import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrawlUrlComponent } from './crawl-url.component';

describe('CrawlUrlComponent', () => {
  let component: CrawlUrlComponent;
  let fixture: ComponentFixture<CrawlUrlComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CrawlUrlComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrawlUrlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
