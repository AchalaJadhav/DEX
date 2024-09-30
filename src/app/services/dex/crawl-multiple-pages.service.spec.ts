import { TestBed } from '@angular/core/testing';

import { CrawlMultiplePagesService } from './crawl-multiple-pages.service';

describe('CrawlMultiplePagesService', () => {
  let service: CrawlMultiplePagesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CrawlMultiplePagesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
