import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DownloadImageService {
// Property to store the DataURL of the solved maze image
  solvedMaze!: string;

  constructor() { }

  // Method to set the DataURL of the solved maze image
  setSolvedMaze(data: string) {
    this.solvedMaze = data;
  }

  // Method to download the solved maze image
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
