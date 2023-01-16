import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ColorpickerComponent } from './colorpicker.component';
import { ElementRef } from '@angular/core';
import {By} from "@angular/platform-browser";

describe('ColorpickerComponent', () => {
  let component: ColorpickerComponent;
  let fixture: ComponentFixture<ColorpickerComponent>;
  let elementRef: ElementRef;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ColorpickerComponent ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ColorpickerComponent);
    component = fixture.componentInstance;
    elementRef = fixture.debugElement.injector.get(ElementRef);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display the image preview and color data when an image is selected', () => {
    // Set up test image file
    const imageFile = new File([], 'test.jpg', { type: 'image/jpeg' });
    const fileInput = fixture.debugElement.query(By.css('#images'));
    fileInput.triggerEventHandler('change', { target: { files: [imageFile] } });
    fixture.detectChanges();

    // Check that the canvas and data elements are displayed
    const canvasElement = fixture.debugElement.query(By.css('canvas'));
    expect(canvasElement).toBeTruthy();
    const dataElement = fixture.debugElement.query(By.css('.data'));
    expect(dataElement).toBeTruthy();
  });

  it('should display the selected image when "displayData" is true', () => {
    component.displayData = true;
    fixture.detectChanges();
    const imageElement = fixture.nativeElement.querySelector('canvas');
    expect(imageElement).toBeTruthy();
  });

  it('should display the color box when "displayCol" is true', () => {
    component.displayCol = true;
    fixture.detectChanges();
    const colorBoxElement = fixture.nativeElement.querySelector('.colorval');
    expect(colorBoxElement).toBeTruthy();
  });

  it('should call the "getPixel" function when the canvas element is clicked', () => {
    spyOn(component, 'getPixel');
    const canvasElement = fixture.nativeElement.querySelector('canvas');
    canvasElement.click();
    expect(component.getPixel).toHaveBeenCalled();
  });

  it('component properly displays the color box and the color of the selected pixel when the "getPixel" function is called with valid x and y coordinates of the pixel on the canvas', () => {
    const colorpickerComponent = new ColorpickerComponent();
    colorpickerComponent.canvas = document.createElement('canvas');
    colorpickerComponent.canvasRenderingContext = colorpickerComponent.canvas.getContext('2d')!;
    colorpickerComponent.canvasRenderingContext.fillStyle = 'rgb(255, 0, 0)';
    colorpickerComponent.canvasRenderingContext.fillRect(0, 0, 10, 10);
    colorpickerComponent.colorBox = document.createElement('div');
    const event = new MouseEvent('click', );
    colorpickerComponent.getPixel(event);
    expect(colorpickerComponent.rgbValue).toEqual('255,0,0');
    expect(colorpickerComponent.hexValue).toEqual('#ff0000');
    expect(colorpickerComponent.colorBox.style.cssText).toEqual('--bgcolorval:rgba(255,0,0,255);');
  });



});
