// This component allows the user to select an image and display the color of a pixel on the image when clicked on.

import {Component, ViewChildren, QueryList, Output, EventEmitter, ViewEncapsulation, ElementRef} from '@angular/core';

// The component decorator provides metadata about the component, including the component's selector, template, and styles.
// It also specifies that the component should use the emulated shadow DOM for encapsulation.
@Component({
  selector: 'app-colorpicker',
  templateUrl: './colorpicker.component.html',
  styleUrls: ['./colorpicker.component.css'],
  encapsulation: ViewEncapsulation.Emulated
})

// The component class includes various properties and methods for selecting an image, displaying the selected image, and displaying the color of a pixel on the image when clicked on.
export class ColorpickerComponent {
  // The "elements" property is a query list of element references that includes the "canvasval" and "colboxval" elements in the component's template.
  @ViewChildren('canvasval, colboxval')
  elements!: QueryList<ElementRef>;

  // The "url" property stores the URL of the selected image.
  // The "displayData" and "displayCol" properties determine whether the image and color box should be displayed, respectively.
  url!: string;
  displayData = false;
  displayCol = false;

  // The "canvas", "canvasRenderingContext", "image", and "colorBox" properties reference the canvas, canvas rendering context, image, and color box elements, respectively.
  canvas!: HTMLCanvasElement;
  canvasRenderingContext!: CanvasRenderingContext2D;
  image!: HTMLImageElement;
  colorBox!: HTMLElement;

  // The "rgbValue" and "hexValue" properties store the RGB and hexadecimal values of the selected color, respectively.
  rgbValue!: string;
  hexValue!: string;

  // The "outputColor" property is an event emitter that emits the selected color as a string when the "getPixel" function is called.
  @Output() outputColor = new EventEmitter<string>();

  // The "readUrl" function is called when the user selects an image file.
  // It displays the image and color box, and reads the selected image file as a data URL.
  readUrl(event: any) {
    this.displayData = true;
    this.displayCol = true;
    if (event.target.files && event.target.files[0]) {
      const reader = new FileReader();
      reader.onload = (event: any) => {
        this.url = event.target.result;
        this.getImg(this.url);
      };
      reader.readAsDataURL(event.target.files[0]);
    }
  }

  // The "getImg" function displays the selected image on the canvas element.
  getImg(url: string) {
    this.colorBox = this.elements.toArray()[1].nativeElement;
    this.colorBox.style.cssText = '--bgcolorval:rgba(0,0,0,0)';
    this.hexValue = '';
    this.rgbValue = '';

    // The "canvas" and "canvasRenderingContext" properties are set to the "canvasval" element and its 2D rendering context, respectively.
    this.canvas = this.elements.first.nativeElement;
    this.canvasRenderingContext = this.canvas.getContext('2d')!;

    // An image element is created and its "src" property is set to the selected image's URL.
    // The canvas is cleared and the image is drawn on the canvas when it finishes loading.
    this.image = document.createElement('img');
    this.image.crossOrigin = 'anonymous';
    this.image.src = this.url;
    this.canvasRenderingContext.clearRect(0, 0, this.canvas.width, this.canvas.height);
    this.image.onload = (() =>
        this.canvasRenderingContext.drawImage(
          this.image,
          0,
          0,
          this.image.width,
          this.image.height,
          0,
          0,
          this.canvas.width,
          this.canvas.height
        )
    );
  }

  // The "getPixel" function is called when the user clicks on the image on the canvas element.
  // It gets the pixel data at the clicked location and converts it to the corresponding color value.
  getPixel(event: MouseEvent) {
    // The "px" variable stores the pixel data at the clicked location.
    // The "dataArray" variable stores the color values of the pixel as an array of integers.
    const px = this.canvasRenderingContext.getImageData(event.offsetX, event.offsetY, 1, 1);
    const dataArray = px.data;

    // The "pixelColor" variable stores the RGB color value of the pixel as a string.
    // The "pixelColorBox" variable stores the RGBA color value of the pixel as a string.
    const pixelColor = `${dataArray[0]},${dataArray[1]},${dataArray[2]}`;
    const pixelColorBox = `rgba(${dataArray[0]},${dataArray[1]},${dataArray[2]},${dataArray[3]})`;

    // The "rgbValue" property is set to the RGB color value of the pixel.
    // The "hexValue" property is set to the hexadecimal color value of the pixel.
    // The "cssText" property of the "colorBox" element is set to the RGBA color value of the pixel.
    // The "outputColor" event is emitted with the hexadecimal and RGB color values of the pixel.
    this.rgbValue = pixelColor;
    const dColor = dataArray[2] + 256 * dataArray[1] + 65536 * dataArray[0];
    this.hexValue = `#${dColor.toString(16)}`;
    this.colorBox.style.cssText = `--bgcolorval:${pixelColorBox}`;
    this.outputColor.emit(`${this.hexValue} ${this.rgbValue}`);
  }
}
