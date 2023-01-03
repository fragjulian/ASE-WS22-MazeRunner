import {Component} from '@angular/core';
import {RestService} from "./rest.service";

@Component({
  selector: 'app-rest-upload',
  templateUrl: './rest-upload.component.html',
  styleUrls: ['./rest-upload.component.css']
})
export class RestUploadComponent {

  uploadImage: any;
  solvedMaze: any;
  successResponse: any;
  //the default rgb value for the wall color in the backend
  rgbvaluewall = '0,0,0';
  //the default rgb value for the obstacle color in the backend
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
    // Reset the solved maze image, error message, popup and successresponse
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

    if (this.checkImage(this.uploadImage)) {
      // The image is valid, so you can proceed with the image upload action

      // Call the solveMaze function of the RestService to send the HTTP request
      this.rest.solveMaze(transformedImage).subscribe(
        // If the request is successful, store the response and display the solved maze image
        (response: any) => {
          console.log(response);
          this.successResponse = 'Solved Maze';
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
    } else {
      // The image is invalid, so do not proceed with the image upload action
      // The error message has already been set by the checkImage() function
    }
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

  checkImage(image: File) {
    if (image.size > 3000000) {
      this.errorMessage = 'Error: Image is too large (max 3 MB)';
      return false;
    }

    // Create an image element and set its src to the File object
    const img = new Image();
    img.src = URL.createObjectURL(image);

    // When the image has finished loading, check if it has less than or equal to three colors
    img.onload = () => {
      const canvas = document.createElement('canvas');
      const context = canvas.getContext('2d');
      if (context) { // Add a null check here
        context.drawImage(img, 0, 0);

        // Get the image data
        const imageData = context.getImageData(0, 0, img.width, img.height);
        const data = imageData.data;

        // Set up a Set to store the unique colors in the image
        const colorSet = new Set();

        // Iterate over the image data and add each color to the Set
        for (let i = 0; i < data.length; i += 4) {
          const color = `rgb(${data[i]}, ${data[i + 1]}, ${data[i + 2]})`;
          colorSet.add(color);
        }

        // If the Set has more than three colors, set the error message and return false
        if (colorSet.size > 3) {
          this.errorMessage = 'Error: Image has more than three colors';
          return false;
        }

        // If the image is valid, clear the error message and return true
        this.errorMessage = '';
        return true;
      } else {
        // The canvas is not supported by the user's browser
        this.errorMessage = 'Error: Canvas is not supported by your browser';
        return false;
      }
    };

    // Return false as a default value in case the image fails to load or the function is called before the img.onload event handler
    return false;
  }
}
