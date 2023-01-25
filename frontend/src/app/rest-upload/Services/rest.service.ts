import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class RestService {

  // Inject the HttpClient module
  constructor(private http: HttpClient) { }

  // Function to send a POST request to the server with the given form data
  solveMaze(formData: FormData) {
    // Set the response type to "blob" to receive a binary response
    return this.http.post('http://localhost:8081/api/maze/image', formData, {responseType: "blob"})
      // Catch and handle any errors that occur
      .pipe(
        catchError((error: any) => {
          console.error(error);
          // Return the error message
          return error.error;
        })
      );
  }
}
