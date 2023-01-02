import {Component} from '@angular/core';
import {RestService} from "./rest.service";

@Component({
  selector: 'app-rest-upload',
  templateUrl: './rest-upload.component.html',
  styleUrls: ['./rest-upload.component.css']
})
export class RestUploadComponent {
  // Declare instance variables
  uploadImage: any;
  solvedMaze: any;
  successResponse: any;
  rgbvaluewall = '0,0,0';
  rgbvalueobstacle = '219,219,219';
  errorMessage: any;
  showPopup = false;

  // Inject the RestService
  constructor(private rest: RestService) {
  }

  // Event handler for when a file is selected
  onSelect(event: any) {
    this.uploadImage = event.addedFiles[0];
  }

  // Event handler for when a file is removed
  onRemove(event: any) {
    this.uploadImage = null;
    // Reset the solved maze image and error message
    this.solvedMaze = null;
    this.errorMessage = null;
    this.showPopup = false;
    this.successResponse = null;
  }

  // Function to submit the image to the REST API for solving
  imageUploadAction() {
    // Reset the solved maze image and error message
    this.solvedMaze = null;
    this.errorMessage = null;
    this.showPopup = false;

    // Create a new FormData object to hold the image and color values
    const transformedImage = new FormData();
    transformedImage.append('image', this.uploadImage, this.uploadImage.name);
    if (this.rgbvaluewall) {
      transformedImage.append('wallcolor', this.rgbvaluewall);
    }
    if (this.rgbvalueobstacle) {
      transformedImage.append('obstaclecolor', this.rgbvalueobstacle);
    }

    // Helper function to convert blob response to DataURL
    const blobToBase64 = (blob: Blob) => {
      const reader = new FileReader();
      reader.readAsDataURL(blob);
      return new Promise(resolve => {
        reader.onloadend = () => {
          resolve(reader.result);
        };
      });
    };

    // Call the solveMaze function of the RestService to send the HTTP request
    this.rest.solveMaze(transformedImage).subscribe(
      // If the request is successful, store the response and display the solved maze image
      (response: any) => {
        console.log(response);
        this.successResponse = 'Answer from server';
        blobToBase64(response).then(res => {
          this.solvedMaze = res;
        });
        this.showPopup = true;
      },
      // If the request fails, store the error message
      (error: any) => {
        console.error(error);
        this.errorMessage = error.message;
      }
    );
  }

  // Function to download the solved maze image
  downloadImage() {
    // Create an anchor element and set its href to the DataURL of the solved maze image
    const link = document.createElement('a');
    link.href = this.solvedMaze;
    // Set the download file name
    link.download = 'solved-maze.jpg';
    // Click the link to trigger the download
    link.click();
  }
}
