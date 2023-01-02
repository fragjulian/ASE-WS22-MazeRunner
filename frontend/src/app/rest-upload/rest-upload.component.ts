import { Component } from '@angular/core';
import { RestService } from './rest.service';

@Component({
  selector: 'app-rest-upload',
  templateUrl: './rest-upload.component.html',
  styleUrls: ['./rest-upload.component.css']
})
export class RestUploadComponent {
  constructor(private rest: RestService) {}

  uploadImage: any;
  solvedMaze: any;
  successResponse: any;
  rgbvaluewall = '0,0,0';
  rgbvalueobstacle = '219,219,219';
  errorMessage: any;

  onSelect(event: any) {
    this.uploadImage = event.addedFiles[0];
  }

  onRemove(event: any) {
    this.uploadImage = null;
  }

  imageUploadAction() {
    this.solvedMaze = null;
    this.errorMessage = null;
    const transformedImage = new FormData();
    transformedImage.append('image', this.uploadImage, this.uploadImage.name);
    if (this.rgbvaluewall) {
      transformedImage.append('wallcolor', this.rgbvaluewall);
    }
    if (this.rgbvalueobstacle) {
      transformedImage.append('obstaclecolor', this.rgbvalueobstacle);
    }

    //Helper function to turn blob response into a DataUrl for the image tags in HTML (https://stackoverflow.com/questions/18650168/convert-blob-to-base64)
    const blobToBase64 = (blob: Blob) => {
      const reader = new FileReader();
      reader.readAsDataURL(blob);
      return new Promise(resolve => {
        reader.onloadend = () => {
          resolve(reader.result);
        };
      });
    };

    this.rest.solveMaze(transformedImage).subscribe(
      (response: any) => {
        console.log(response);
        this.successResponse = 'Answer from server';
        blobToBase64(response).then(res => {
          //Used this link to know how to use the DataURL: https://en.wikipedia.org/wiki/Data_URI_scheme
          this.solvedMaze = res;
        });
      },
      (error: any) => {
        console.error(error);
        // Set the error message to be displayed to the user
        this.errorMessage = error.message;
      }
    );
  }
}
