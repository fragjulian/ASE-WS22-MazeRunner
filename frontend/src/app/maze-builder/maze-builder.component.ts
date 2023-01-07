import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";

class Position {
  x: number;
  y: number;

  constructor(x: number, y: number) {
    this.x = x;
    this.y = y;
  }

  public equals(obj: Object): boolean {
    if (obj instanceof Position)
      return obj.x == this.x && obj.y == this.y;

    return false;
  }
}

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
  postResponse: any;

  canvas: HTMLCanvasElement | undefined;
  context: CanvasRenderingContext2D | undefined;
  colorPicker: HTMLElement | undefined;
  private walls = new Set<Position>();
  brushColor = this.initialBrushColor;//todo change to array
  pixelSize = 15;
  cursorPosX = 0;
  cursorPosY = 0;
  private currentPath: Position[] = [];
  selectedBrush = "wall"

  isDrawing = false;
  private obstacles = new Set<Position>();

  constructor(private httpClient: HttpClient) {
  }

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
    this.getSolvedPath();//automatically get and draw the solved path in the maze editor
  }

  clearMaze() {
    const width = this.canvas!.width;
    const height = this.canvas!.height;
    this.context!.clearRect(0, 0, width, height);
    this.walls = new Set<Position>();
    this.obstacles = new Set<Position>();
  }

  getSolvedPath() {
    console.log("getting solved path");
    //var returnObject={walls:this.walls}
    let returnObject = {
      walls: Array.from(this.walls.values()),
      obstacles: Array.from(this.obstacles.values())
    }
    this.httpClient.post(`http://localhost:8081/api/maze/path?sizeX=${this.canvas!.width / this.pixelSize}&sizeY=${this.canvas!.height / this.pixelSize}`, returnObject, {//todo set size correctly
      observe: 'response',
      responseType: 'json'
    })
      .subscribe((response) => {

          if (response.status === 200 && response.body != null && 'path' in response.body) {
            console.log(response.body);
            this.clearCurrentPath();
            this.postResponse = response
            this.currentPath = [];
            for (let path of this.postResponse.body.path) {
              this.currentPath.push(new Position(path.x, path.y));

              this.drawPixel(path.x, path.y, 'red')
            }
          }
        }
      );
  }

  private drawPixelAtCurrentMousePosition(offsetX: number, offsetY: number) {
    this.cursorPosX = Math.floor(offsetX / this.pixelSize);
    this.cursorPosY = Math.floor(offsetY / this.pixelSize);
    this.drawPixel(this.cursorPosX, this.cursorPosY, this.brushColor);
    if (this.selectedBrush == "wall")
      this.walls.add(new Position(this.cursorPosX, this.cursorPosY));
    if (this.selectedBrush == "obstacle")
      this.obstacles.add(new Position(this.cursorPosX, this.cursorPosY));
  }

  private drawPixel(xCoord: number, yCoord: number, color: string) {
    this.context!.fillStyle = color;
    const startX = xCoord * this.pixelSize;
    const startY = yCoord * this.pixelSize;
    this.context!.fillRect(startX, startY, this.pixelSize, this.pixelSize);
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

  private clearCurrentPath() {
    for (let current of this.currentPath) {
      if (!this.walls.has(current)) {//only clear pixels where no wall has been drawn todo has not correctly returning
        this.context!.clearRect(current.x * this.pixelSize, current.y * this.pixelSize, this.pixelSize, this.pixelSize);
      } else {
        this.drawPixel(current.x * this.pixelSize, current.y * this.pixelSize, this.brushColor);//redraw wall
      }
    }

  }
}
