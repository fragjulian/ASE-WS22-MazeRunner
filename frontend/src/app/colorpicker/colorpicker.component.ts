// Source for Image Picker: https://stackblitz.com/edit/angular-image-color-picker?file=src%2Fapp%2Fapp.component.css,src%2Fapp%2Fimgcol%2Fimgcol.component.html,src%2Fapp%2Fimgcol%2Fimgcol.component.ts,package.json
import { Component,ViewChild,Output,EventEmitter,ElementRef,ViewEncapsulation } from '@angular/core';
@Component({
  selector: 'app-colorpicker',
  templateUrl: './colorpicker.component.html',
  styleUrls: ['./colorpicker.component.css'],
  encapsulation:ViewEncapsulation.Emulated
})
export class ColorpickerComponent {

  @ViewChild('canvasval')
  canvasval!: ElementRef;
  @ViewChild('colboxval')colboxval:any;
  url: any;
  displayData=false;
  displayCol=false;
   canvas: any;
   canvasrenderingcontext!: CanvasRenderingContext2D;
   image: any;
   colorbox: any;
   rgbvalue: any;
   hexvalue: any;


  @Output() outputColor=new EventEmitter();

  readUrl(event:any) {
    this.displayData=true;
    this.displayCol=true;
    if (event.target.files && event.target.files[0])  {
      var reader = new FileReader();
      reader.onload = (event:any) => {
        this.url = event.target.result;
        this.getimg(this.url);
      }
      reader.readAsDataURL(event.target.files[0]);
    }
  }

  getimg(url:string)
  {
    this.colorbox=this.colboxval.nativeElement;
    this.colorbox.style.cssText = "--bgcolorval:rgba(0,0,0,0)";
    this.hexvalue="";
    this.rgbvalue="";
    this.canvas = this.canvasval.nativeElement;
    this.canvasrenderingcontext = this.canvas.getContext("2d");
    this.image=document.createElement("img"),
      this.image.crossOrigin = 'anonymous';
    this.image.src = this.url;
    this.canvasrenderingcontext.clearRect(0, 0, this.canvas.width, this.canvas.height);
    this.image.onload=(()=>
      this.canvasrenderingcontext.drawImage(this.image, 0, 0,this.image.width,this.image.height,0, 0,this.canvas.width, this.canvas.height));
  }

  getPixel(event: any)
  {
    var boundingRect=this.canvas.getBoundingClientRect();
    var x=event.clientX-boundingRect.left;
    var y=event.clientY-boundingRect.top;
    var px=this.canvasrenderingcontext.getImageData(x,y,1,1);
    var data_array=px.data;
    var pixelColor=data_array[0]+","+data_array[1]+","+data_array[2];

    //var pixelColor2=data_array[0]+","+data_array[1]+","+data_array[2]+","+data_array[3]+")";

    this.rgbvalue=pixelColor;
    var dColor = data_array[2] + 256 * data_array[1] + 65536 * data_array[0];
    this.hexvalue=('#'+dColor.toString(16));
    this.colorbox.style.cssText = "--bgcolorval:"+pixelColor;
    this.outputColor.emit(this.hexvalue+" "+this.rgbvalue);
  }


}
