import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-rest-upload',
  templateUrl: './rest-upload.component.html',
  styleUrls: ['./rest-upload.component.css']
})

//Changed version of this tutorial: https://www.techgeeknext.com/angular-upload-image
export class RestUploadComponent {

  constructor(private httpClient: HttpClient) {
  }


  uploadImage: any;
  solvedMaze: any;
  postResponse: any;
  successResponse: any;
  rgbvaluewall: string = '0,0,0';
  rgbvalueobstacle: string = '219,219,219';

  /***
   * Saves the image to a variable.
   * @param event
   */
  onSelect(event: any) {
    this.uploadImage = event.addedFiles[0];
    console.log(this.uploadImage);
  }

  onRemove(event: any) {
    console.log(event)
    this.uploadImage = null;
  }

  /***
   * This method takes the uploaded image, changes it to form-data with 'image' as key and the image as value.
   * After that a post request is sent to the server. The solved image is sent back as a blob and changed into
   * a DataURL. Everything is updated automatically in the html file.
   */
  imageUploadAction() {

    //Transform image into FormData for the post request
    const transformedImage = new FormData();
    transformedImage.append('image', this.uploadImage, this.uploadImage.name);
    //transformedImage.append('walldetector', 'colorwalldetector');
    if(this.rgbvaluewall) {
      transformedImage.append('wallcolor', this.rgbvaluewall);
    }
    if(this.rgbvalueobstacle) {
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


    /*
    * Post request that sends the transformed image to the server. It gets the solved image as an answer.
    * Looked up the response types here: https://stackoverflow.com/questions/46408537/angular-httpclient-http-failure-during-parsing
    * Port 8081 for Deployment on live server
    */
    this.httpClient.post('http://localhost:8081/api/maze/colorwalldetector/realdistanceheuristic/depthfirst', transformedImage, {
      observe: 'response',
      responseType: 'blob'
    })
      .subscribe((response) => {

          if (response.status === 200) {

            this.postResponse = response;

            this.successResponse = 'Successful';

            blobToBase64(this.postResponse.body).then(res => {
              //Used this link to know how to use the DataURL: https://en.wikipedia.org/wiki/Data_URI_scheme
              this.solvedMaze = res;
            });

          } else {

            this.successResponse = 'Image not uploaded due to some error!';

          }
        }
      );
  }
}
