import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { RestService } from './rest.service';
import {HttpErrorResponse, HttpHeaders} from "@angular/common/http";

describe('RestService', () => {
  let service: RestService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [RestService]
    });
    service = TestBed.inject(RestService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should send a POST request to the correct URL with the correct payload and return the expected response', () => {
    // Define the test data
    const formData = new FormData();
    formData.append('file', new File([], 'test.png'));
    const mockResponse = new Blob();

    // Call the service method
    service.solveMaze(formData).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    // Expect a single request to be made to the specified URL with the correct payload
    const req = httpMock.expectOne('http://localhost:8081/api/maze/colorwalldetector/realdistanceheuristic/depthfirst');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(formData);

    // Set the mock response
    req.flush(mockResponse);
  });


  afterEach(() => {
    // Ensure that there are no outstanding requests
    httpMock.verify();
  });
});
