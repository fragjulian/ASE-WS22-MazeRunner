import { ComponentFixture, TestBed } from '@angular/core/testing';


import { ColorpickerComponent } from './colorpicker.component';
import {By} from "@angular/platform-browser";

describe('ColorpickerComponent', () => {
  let component: ColorpickerComponent;
  let fixture: ComponentFixture<ColorpickerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ColorpickerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ColorpickerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display the image preview and color data when an image is selected', () => {
    // Set up test image file
    const imageFile = new File([], 'test.jpg', { type: 'image/jpeg' });
    const fileInput = fixture.debugElement.query(By.css('#colorpicker-body-input'));
    fileInput.triggerEventHandler('change', { target: { files: [imageFile] } });
    fixture.detectChanges();

    // Check that the canvas and data elements are displayed
    const canvasElement = fixture.debugElement.query(By.css('canvas'));
    expect(canvasElement).toBeTruthy();
    const dataElement = fixture.debugElement.query(By.css('.data'));
    expect(dataElement).toBeTruthy();
  });
});
