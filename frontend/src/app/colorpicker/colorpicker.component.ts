import {Component, ViewChildren, QueryList, Output, EventEmitter, ViewEncapsulation, ElementRef} from '@angular/core';

@Component({
  selector: 'app-colorpicker',
  templateUrl: './colorpicker.component.html',
  styleUrls: ['./colorpicker.component.css'],
  encapsulation: ViewEncapsulation.Emulated
})
export class ColorpickerComponent {
  @ViewChildren('canvasval, colboxval')
  elements!: QueryList<ElementRef>;
  url: any;
  displayData = false;
  displayCol = false;
  canvas: any;
  canvasRenderingContext!: CanvasRenderingContext2D;
  image: any;
  colorBox: any;
  rgbValue: any;
  hexValue: any;

  @Output() outputColor = new EventEmitter<string>();

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

  getImg(url: string) {
    this.colorBox = this.elements.toArray()[1].nativeElement;
    this.colorBox.style.cssText = '--bgcolorval:rgba(0,0,0,0)';
    this.hexValue = '';
    this.rgbValue = '';
    this.canvas = this.elements.first.nativeElement;
    this.canvasRenderingContext = this.canvas.getContext('2d');
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

  getPixel(event: MouseEvent) {
    const px = this.canvasRenderingContext.getImageData(event.offsetX, event.offsetY, 1, 1);
    const dataArray = px.data;
    const pixelColor = `${dataArray[0]},${dataArray[1]},${dataArray[2]}`;
    const pixelColorBox = `rgba(${dataArray[0]},${dataArray[1]},${dataArray[2]},${dataArray[3]})`;

    this.rgbValue = pixelColor;
    const dColor = dataArray[2] + 256 * dataArray[1] + 65536 * dataArray[0];
    this.hexValue = `#${dColor.toString(16)}`;
    this.colorBox.style.cssText = `--bgcolorval:${pixelColorBox}`;
    this.outputColor.emit(`${this.hexValue} ${this.rgbValue}`);
  }
}
