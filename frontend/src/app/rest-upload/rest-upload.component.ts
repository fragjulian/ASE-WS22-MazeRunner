import {Component} from '@angular/core';
import {RestService} from "./Services/rest.service";
import {Observable, Observer} from 'rxjs';
import {DownloadImageService} from "./Services/download-image.service";
import {ToastrService} from "ngx-toastr";
import DevExpress from "devextreme";

import data = DevExpress.data;

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
  rgbvaluestart = '196,4,36';
  rgbvalueend = '63,72,204';
  safetydistance = '1';

  // Inject the RestService
  constructor(private rest: RestService, private downloadService: DownloadImageService, private toastr: ToastrService) {
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
    if(this.rgbvaluestart){
      transformedImage.append('startcolor', this.rgbvaluestart)
    }
    if(this.rgbvalueend){
      transformedImage.append('endcolor', this.rgbvalueend)
    }
    if(this.safetydistance){
      transformedImage.append('safetydistance', this.safetydistance)
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

    this.checkImage(this.uploadImage).subscribe(
      isValid => {
        if (isValid) {
          // The image is valid, so you can proceed with the image upload action

          // Call the solveMaze function of the RestService to send the HTTP request
          this.rest.solveMaze(transformedImage).subscribe(
            // If the request is successful, store the response and display the solved maze image
            (response: any) => {
              console.log(response);
              this.successResponse = 'Solved Maze';
              blobToBase64(response).then(res => {
                this.solvedMaze = res;
                if (typeof res === "string") {
                  this.downloadService.setSolvedMaze(res);
                }
              });
              this.showPopup = true;
            },
            // If the request fails, store the error message
            (error: any) => {
              console.error(error);
              this.errorMessage = error.message;
              this.toastr.error("This maze is unsolvable");
            }
          );
        } else {
          // The image is invalid, so do not proceed with the image upload action
          // The error message has already been set by the checkImage() function
        }
      });
  }


  // Function to download the solved maze image
  downloadImage() {
    this.downloadService.downloadImage();
  }

  checkImage(image: File): Observable<boolean> {
    return Observable.create((observer: Observer<boolean>) => {
      if (image.size > 3000000) {
        this.errorMessage = 'Error: Image is too large (max 3 MB)';
        observer.next(false);
        observer.complete();
        return;
      }

      // Create an image element and set its src to the File object
      const img = new Image();
      img.src = URL.createObjectURL(image);

      // When the image has finished loading, check the image for color quantity
      img.onload = () => {
        const canvas = document.createElement('canvas');
        const context = canvas.getContext('2d');
        if (context) {
          context.drawImage(img, 0, 0);


          const imageData = context.getImageData(0, 0, img.width, img.height);
          const data = imageData.data;


          const colorSet = new Set();

          // Iterate over the image data and add each color to the Set
          for (let i = 0; i < data.length; i += 4) {
            const color = `rgb(${data[i]}, ${data[i + 1]}, ${data[i + 2]})`;
            colorSet.add(color);
          }

          // If the Set has more than x colors, set the error message and return false. Sets the limit value for the color quantity in the uploaded picture
          if (colorSet.size > 2000) {
            this.errorMessage = 'Error: Image has more than three colors';
            observer.next(false);
            observer.complete();
            return;
          }

          // If the image is valid, clear the error message and return true
          this.errorMessage = '';
          observer.next(true);
          observer.complete();
        } else {
          // The canvas is not supported by the user's browser
          this.errorMessage = 'Error: Canvas is not supported by your browser';
          observer.next(false);
          observer.complete();
        }
      };
    });
  }
}
