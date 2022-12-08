import { Component } from '@angular/core';

@Component({
  selector: 'mr-upload-file',
  templateUrl: './mr-upload-file.component.html',
  styleUrls: ['./mr-upload-file.component.css']
})
export class MrUploadFileComponent {
  isDropZoneActive = false;

  imageSource = '';

  textVisible = true;

  progressVisible = false;

  progressValue = 0;

  constructor() {
    this.onDropZoneEnter = this.onDropZoneEnter.bind(this);
    this.onDropZoneLeave = this.onDropZoneLeave.bind(this);
    this.onUploaded = this.onUploaded.bind(this);
    this.onProgress = this.onProgress.bind(this);
    this.onUploadStarted = this.onUploadStarted.bind(this);
  }

  // @ts-ignore
  onDropZoneEnter(e) {
    if (e.dropZoneElement.id === 'dropzone-external') { this.isDropZoneActive = true; }
  }

  // @ts-ignore
  onDropZoneLeave(e) {
    if (e.dropZoneElement.id === 'dropzone-external') { this.isDropZoneActive = false; }
  }

  // @ts-ignore
  onUploaded(e) {
    const file = e.file;
    const fileReader = new FileReader();
    fileReader.onload = () => {
      this.isDropZoneActive = false;
      this.imageSource = fileReader.result as string;
    };
    fileReader.readAsDataURL(file);
    this.textVisible = false;
    this.progressVisible = false;
    this.progressValue = 0;
  }

  // @ts-ignore
  onProgress(e) {
    this.progressValue = e.bytesLoaded / e.bytesTotal * 100;
  }

  onUploadStarted() {
    this.imageSource = '';
    this.progressVisible = true;
  }
}
