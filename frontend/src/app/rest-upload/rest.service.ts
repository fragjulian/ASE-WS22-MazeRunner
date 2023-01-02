import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {catchError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RestService {

  constructor(private http: HttpClient) { }

  solveMaze(formData: FormData) {
    // Send the POST request to the server
    return this.http.post('http://localhost:8081/api/maze/colorwalldetector/realdistanceheuristic/depthfirst', formData, { responseType: "blob" })
      .pipe(
        catchError((error: any) => {
          console.error(error);
          // Handle the error and return a suitable value
          return error.error;
        })
      );
  }
}
