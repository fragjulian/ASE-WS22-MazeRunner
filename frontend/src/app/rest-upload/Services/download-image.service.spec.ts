import { TestBed } from '@angular/core/testing';

import { DownloadImageService } from './download-image.service';

describe('DownloadImageService', () => {
  let service: DownloadImageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DownloadImageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
