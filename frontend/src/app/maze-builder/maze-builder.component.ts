import {Component} from '@angular/core';

@Component({
  selector: 'app-maze-canvas',
  templateUrl: './maze-builder.component.html',
  styleUrls: ['./maze-builder.component.css']
})
export class MazeBuilderComponent {

  private readonly initialBrushColor = 'black';

  // usually the left mouse button
  private readonly primaryMouseButton = 1;

  private readonly mazeDefaultFileName = 'my-maze.png';

  canvas: HTMLCanvasElement | undefined;
  context: CanvasRenderingContext2D | undefined;
  colorPicker: HTMLElement | undefined;

  pixelSize = 20;
  cursorPosX = 0;
  cursorPosY = 0;
  brushColor = this.initialBrushColor;

  isDrawing = false;

  ngOnInit() {
    this.canvas = document.getElementById('maze-canvas') as HTMLCanvasElement;

    // "2d" → a two-dimensional rendering context
    this.context = this.canvas.getContext('2d') ?? undefined;

    this.colorPicker = document.getElementById('color-picker') as HTMLElement;

    this.canvas.addEventListener('click', this.handleClick.bind(this));
    this.canvas.addEventListener('mousemove', this.handleMouseMove.bind(this));
    this.canvas.addEventListener('mousedown', this.handleMouseDown.bind(this));
    this.canvas.addEventListener('mouseup', this.handleMouseUp.bind(this));
  }

  handleClick(event: MouseEvent) {
    this.drawPixelAtCurrentMousePosition(event.offsetX, event.offsetY);
  }

  handleMouseMove(event: MouseEvent) {
    if (!this.isDrawing) {
      return;
    }

    if (event.buttons !== this.primaryMouseButton) {
      return;
    }

    this.drawPixelAtCurrentMousePosition(event.offsetX, event.offsetY);
  }

  handleMouseDown(event: MouseEvent) {
    this.isDrawing = true;
  }

  handleMouseUp(event: MouseEvent) {
    this.isDrawing = false;
  }

  private drawPixelAtCurrentMousePosition(offsetX: number, offsetY: number) {
    this.cursorPosX = Math.floor(offsetX / this.pixelSize);
    this.cursorPosY = Math.floor(offsetY / this.pixelSize);
    this.drawPixel(this.cursorPosX, this.cursorPosY);
  }

  private drawPixel(xCoord: number, yCoord: number) {
    this.context!.fillStyle = this.brushColor;
    const startX = xCoord * this.pixelSize;
    const startY = yCoord * this.pixelSize;
    this.context!.fillRect(startX, startY, this.pixelSize, this.pixelSize);
  }

  clearMaze() {
    const width = this.canvas!.width;
    const height = this.canvas!.height;
    this.context!.clearRect(0, 0, width, height);
  }

  exportMazeAsImage() {
    const dataUrl = this.canvas!.toDataURL();
    const link = document.createElement('a');
    link.href = dataUrl;
    link.download = this.mazeDefaultFileName;
    link.click();
  }

  drawMazeBorder() {
    this.context!.strokeStyle = this.brushColor;
    this.context!.lineWidth = 2 * this.pixelSize;
    const width = this.canvas!.width;
    const height = this.canvas!.height;
    this.context!.strokeRect(0, 0, width, height);
  }

  openColorPickerDialog() {
    this.colorPicker!.click();
  }
}
