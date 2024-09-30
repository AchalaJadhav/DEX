import { TestBed } from '@angular/core/testing';

import { UserDetailsFromBackendService } from './user-details-from-backend.service';

describe('UserDetailsFromBackendService', () => {
  let service: UserDetailsFromBackendService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserDetailsFromBackendService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
