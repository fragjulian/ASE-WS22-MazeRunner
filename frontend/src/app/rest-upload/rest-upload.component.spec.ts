import {ComponentFixture, TestBed} from '@angular/core/testing';
import {FormsModule} from "@angular/forms";

import {RestUploadComponent} from './rest-upload.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {NgxDropzoneModule} from "ngx-dropzone";
import {DownloadImageService} from "./Services/download-image.service";
import {By} from "@angular/platform-browser";
import {ToastrModule} from "ngx-toastr";

describe('RestUploadComponent', () => {
  let component: RestUploadComponent;
  let fixture: ComponentFixture<RestUploadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule,
        NgxDropzoneModule,
        FormsModule,
        ToastrModule.forRoot()],
      declarations: [RestUploadComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RestUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have existing title element', () => {
    const header = fixture.debugElement.nativeElement.querySelector('.restupload-heading');
    expect(header).toBeTruthy();
  });

  it('should have existing title element for the rgb value of the wall', () => {
    const title_wall = fixture.debugElement.nativeElement.querySelector('#title-rgb-wall');
    expect(title_wall).toBeTruthy();
  });

  it('should have existing title element for the rgb value of the obstacle', () => {
    const title_obstacle = fixture.debugElement.nativeElement.querySelector('#title-rgb-obstacle');
    expect(title_obstacle).toBeTruthy();
  });

  it('solve-maze button should exist', () => {
    const button = fixture.debugElement.nativeElement.querySelector('#solve-maze-button');
    expect(button).toBeTruthy();
  });

  it('solve-maze button should have text "Solve Maze"', () => {
    const button = fixture.debugElement.nativeElement.querySelector('#solve-maze-button');
    expect(button.value).toBe('Solve Maze');
  });

  it('solve-maze button should have correct uitestid attribute', () => {
    const btn = fixture.debugElement.nativeElement.querySelector('#solve-maze-button');
    expect(btn.getAttribute('uitestid')).toBe('solve-maze');
  });

  it('input text field for wall rgb values should exist', () => {
    const button = fixture.debugElement.nativeElement.querySelector('#rgbvaluewall');
    expect(button).toBeTruthy();
  });

  it('input text field for obstacle rgb values should exist', () => {
    const button = fixture.debugElement.nativeElement.querySelector('#rgbvalueobstacle');
    expect(button).toBeTruthy();
  });

  it('input text field for wall rgb values should have correct uitestid attribute', () => {
    const btn = fixture.debugElement.nativeElement.querySelector('#rgbvaluewall');
    expect(btn.getAttribute('uitestid')).toBe('rgb-wall-input');
  });

  it('input text field for obstacle rgb values should have correct uitestid attribute', () => {
    const btn = fixture.debugElement.nativeElement.querySelector('#rgbvalueobstacle');
    expect(btn.getAttribute('uitestid')).toBe('rgb-obstacle-input');
  });

  it('should set the uploadImage property to the selected file', () => {
    component.onSelect({ addedFiles: [{name: 'image.jpg', size: 1000}] });
    expect(component.uploadImage).toEqual({name: 'image.jpg', size: 1000});
  });

  it('should reset the solvedMaze, errorMessage, showPopup, and successResponse properties', () => {
    component.solvedMaze = 'image.jpg';
    component.errorMessage = 'Error';
    component.showPopup = true;
    component.successResponse = 'Success';

    component.onRemove({});
    expect(component.solvedMaze).toBeNull();
    expect(component.errorMessage).toBeNull();
    expect(component.showPopup).toBeFalse();
    expect(component.successResponse).toBeNull();
  });

  it('should call the DownloadImageService\'s downloadImage function', () => {
    const downloadService = TestBed.get(DownloadImageService);
    const spy = spyOn(downloadService, 'downloadImage');
    component.downloadImage();
    expect(spy).toHaveBeenCalled();
  });

  it('should return an Observable that emits a boolean value indicating whether the image is valid', () => {
    const validImage = new File([], 'image.jpg', { type: 'image/jpeg' });
    const invalidImage = new File([], 'image.txt', { type: 'text/plain' });
    const validObservable = component.checkImage(validImage);
    const invalidObservable = component.checkImage(invalidImage);
    validObservable.subscribe(isValid => {
      expect(isValid).toBeTrue();
    });
    invalidObservable.subscribe(isValid => {
      expect(isValid).toBeFalse();
    });
  });

  it('should set the errorMessage property when the image is invalid', () => {
    const invalidImage = new File([], 'image.txt', { type: 'text/plain' });
    component.checkImage(invalidImage).subscribe(() => {
      expect(component.errorMessage).toEqual('Invalid file type. Only jpeg and png are allowed.');
    });
  });

  it('should display the "Maze Upload" heading', () => {
    const mazeHeader = fixture.debugElement.query(By.css('[uitestid="maze-header"]')).nativeElement;
    expect(mazeHeader.textContent).toEqual('Maze Upload');
  });

  it('should display the dropzone component', () => {
    const dropzone = fixture.debugElement.query(By.css('ngx-dropzone'));
    expect(dropzone).toBeTruthy();
  });

  it('should disable the "Solve Maze" button when no image is selected', () => {
    const solveMazeButton = fixture.debugElement.query(By.css('[uitestid="solve-maze"]')).nativeElement;
    expect(solveMazeButton.disabled).toBeTrue();
  });

  it('should enable the "Solve Maze" button when an image is selected', () => {
    component.uploadImage = { name: 'image.jpg', size: 1000 };
    fixture.detectChanges();
    const solveMazeButton = fixture.debugElement.query(By.css('[uitestid="solve-maze"]')).nativeElement;
    expect(solveMazeButton.disabled).toBeFalse();
  });

  it('should display the RGB Value of the Wall input field', () => {
    const rgbWallInput = fixture.debugElement.query(By.css('[uitestid="rgb-wall-input"]')).nativeElement;
    expect(rgbWallInput).toBeTruthy();
  });

  it('should display the RGB Value of the Obstacle input field', () => {
    const rgbObstacleInput = fixture.debugElement.query(By.css('[uitestid="rgb-obstacle-input"]')).nativeElement;
    expect(rgbObstacleInput).toBeTruthy();
  });

  it('should display the success message when the successResponse property is truthy', () => {
    component.successResponse = 'Solved Maze';
    fixture.detectChanges();
    const successMessage = fixture.debugElement.query(By.css('.success'));
    expect(successMessage).toBeTruthy();
  });

  /*it('should display the error message when the errorMessage property is truthy', () => {
    component.errorMessage = 'Error';
    fixture.detectChanges();
    const errorMessage = fixture.debugElement.query(By.css('.error'));
    expect(errorMessage).toBeTruthy();
  });*/

  it('should display the popover for the solved maze image when the showPopup property is truthy', () => {
    component.showPopup = true;
    fixture.detectChanges();
    const popupOverlay = fixture.debugElement.query(By.css('.popup-overlay'));
    expect(popupOverlay).toBeTruthy();
  });

  it('should display the download button for the solved maze image when the showPopup property is truthy', () => {
    component.showPopup = true;
    fixture.detectChanges();
    const downloadButton = fixture.debugElement.query(By.css('.btn-outline-dark'));
    expect(downloadButton).toBeTruthy();
  });

});

