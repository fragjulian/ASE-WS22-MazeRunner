import {Component} from '@angular/core';

@Component({
  selector: 'app-maze-canvas',
  templateUrl: './maze-builder.component.html',
  styleUrls: ['./maze-builder.component.css']
})
export class MazeBuilderComponent {
  // Initial color of the brush
  private readonly initialBrushColor = 'black';
  // Mouse button used to draw on the canvas
  // Usually the left mouse button
  private readonly primaryMouseButton = 1;
  // Default file name when exporting the maze as an image
  private readonly mazeDefaultFileName = 'my-maze.png';


  canvas: HTMLCanvasElement | undefined;

  context: CanvasRenderingContext2D | undefined;

  colorPicker: HTMLElement | undefined;

  // Size of the pixels in the maze
  pixelSize = 15;
  // Current x-coordinate of the cursor on the canvas
  cursorPosX = 0;
  // Current y-coordinate of the cursor on the canvas
  cursorPosY = 0;
  // Current color of the brush
  brushColor = this.initialBrushColor;
  // Flag: the user is drawing as long as they have the primary mouse button pressed
  isDrawing = false;

  ngOnInit() {

    this.canvas = document.getElementById('maze-canvas') as HTMLCanvasElement;


    this.context = this.canvas.getContext('2d') ?? undefined;


    this.colorPicker = document.getElementById('color-picker') as HTMLElement;

    // Add event listeners to the canvas element
    this.canvas.addEventListener('click', this.handleClick.bind(this));
    this.canvas.addEventListener('mousemove', this.handleMouseMove.bind(this));
    this.canvas.addEventListener('mousedown', this.handleMouseDown.bind(this));
    this.canvas.addEventListener('mouseup', this.handleMouseUp.bind(this));
  }


  handleClick(event: MouseEvent) {
    this.drawPixelAtCurrentMousePosition(event.offsetX, event.offsetY);
  }


  handleMouseMove(event: MouseEvent) {
    // Check if the user is currently drawing on the canvas
    if (!this.isDrawing) {
      return;
    }
    // Check if the primary mouse button is being pressed
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

  // Draws a pixel at the specified coordinates on the canvas
  private drawPixelAtCurrentMousePosition(offsetX: number, offsetY: number) {
    // Calculate the grid coordinates for the pixel
    this.cursorPosX = Math.floor(offsetX / this.pixelSize);
    this.cursorPosY = Math.floor(offsetY / this.pixelSize);
    // Draw the pixel at the calculated coordinates
    this.drawPixel(this.cursorPosX, this.cursorPosY);
  }

  // Draws a pixel at the specified grid coordinates on the canvas
  private drawPixel(xCoord: number, yCoord: number) {
    // Set the fill color to the current brush color
    this.context!.fillStyle = this.brushColor;
    // Calculate the pixel's starting coordinates on the canvas
    const startX = xCoord * this.pixelSize;
    const startY = yCoord * this.pixelSize;
    // Draw the pixel on the canvas
    this.context!.fillRect(startX, startY, this.pixelSize, this.pixelSize);
  }

  // Clears the entire canvas
  clearMaze() {
    // Get the width and height of the canvas
    const width = this.canvas!.width;
    const height = this.canvas!.height;
    // Clear the canvas using the clearRect method of the canvas context
    this.context!.clearRect(0, 0, width, height);
  }

  // Exports the current state of the canvas as an image file
  exportMazeAsImage() {
    // Get the data URL for the canvas
    const dataUrl = this.canvas!.toDataURL();
    // Create a link element
    const link = document.createElement('a');
    // Set the link's href to the data URL
    link.href = dataUrl;
    // Set the download attribute of the link to the default file name for the maze image
    link.download = this.mazeDefaultFileName;
    // Click the link to trigger the download
    link.click();
  }

  // Draws a border around the entire canvas
  drawMazeBorder() {
    // Set the stroke style to the current brush color
    this.context!.strokeStyle = this.brushColor;
    // Set the line width
    this.context!.lineWidth = 2 * this.pixelSize;
    // Get the width and height of the canvas
    const width = this.canvas!.width;
    const height = this.canvas!.height;
    // Draw the border around the canvas using the strokeRect method of the canvas context
    this.context!.strokeRect(0, 0, width, height);
  }

  // Opens the color picker dialog
  openColorPickerDialog() {
    this.colorPicker!.click();
  }
}
