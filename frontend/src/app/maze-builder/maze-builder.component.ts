import {Component} from '@angular/core';
import {MazeBuilderAutoSolveService} from "../maze-builder-auto-solve.service";

export class Position {
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
  private readonly initialBrushColors = ['black', 'gray', 'green'];

  // usually the left mouse button
  private readonly primaryMouseButton = 1;

  private readonly mazeDefaultFileName = 'my-maze.png';
  postResponse: any;

  canvas: HTMLCanvasElement | undefined;
  context: CanvasRenderingContext2D | undefined;
  colorPicker: HTMLElement | undefined;
  private walls = new Set<Position>();
  brushColor = this.initialBrushColor;
  pixelSize = 15;
  cursorPosX = 0;
  cursorPosY = 0;
  private currentPath: Position[] = [];
  private brushColors = this.initialBrushColors;
  selectedBrush = "wall"

  isDrawing = false;
  private obstacles = new Set<Position>();

  constructor(private mazeBuilderAutoSolve: MazeBuilderAutoSolveService) {
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

  saveBrushColor() {
    if (this.selectedBrush == "wall")
      this.brushColors[0] = this.brushColor;
    if (this.selectedBrush == "obstacles")
      this.brushColors[1] = this.brushColor
    if (this.selectedBrush == "other")
      this.brushColors[2] = this.brushColor
  }

  restoreBrushColor() {
    if (this.selectedBrush == "wall")
      this.brushColor = this.brushColors[0];
    if (this.selectedBrush == "obstacle")
      this.brushColor = this.brushColors[1]
    if (this.selectedBrush == "other")
      this.brushColor = this.brushColors[2]

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
    this.mazeBuilderAutoSolve.downloadSolutionPath(this.canvas!.width / this.pixelSize, this.canvas!.height / this.pixelSize, this.walls, this.obstacles)
      .subscribe((response: any) => {//todo use angular to directly convert this to array and not use any
          this.postResponse = response;
          this.drawPath();
        }
      );
  }

  drawPath() {
    this.clearCurrentPath();
    this.currentPath = [];
    for (let path of this.postResponse.body.path) {
      this.currentPath.push(new Position(path.x, path.y));
      this.drawPixel(path.x, path.y, 'red')
    }
  }

  openColorPickerDialog() {
    this.colorPicker!.click();
    this.saveBrushColor()
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

  private drawPixelAtCurrentMousePosition(offsetX: number, offsetY: number) {
    this.cursorPosX = Math.floor(offsetX / this.pixelSize);
    this.cursorPosY = Math.floor(offsetY / this.pixelSize);
    this.restoreBrushColor();
    this.drawPixel(this.cursorPosX, this.cursorPosY, this.brushColor);
    if (this.selectedBrush == "wall")
      this.walls.add(new Position(this.cursorPosX, this.cursorPosY));
    if (this.selectedBrush == "obstacle")
      this.obstacles.add(new Position(this.cursorPosX, this.cursorPosY));
  }

  private clearCurrentPath() {
    for (let current of this.currentPath) {
      if (this.setHasPosition(this.walls, current)) {//only clear pixels where no wall has been drawn notice: has not correctly returning
        this.drawPixel(current.x * this.pixelSize, current.y * this.pixelSize, this.brushColors[0]);//redraw wall
      } else if (this.setHasPosition(this.obstacles, current))
        this.drawPixel(current.x * this.pixelSize, current.y * this.pixelSize, this.brushColors[1]);//redraw wall
      else {
        this.context!.clearRect(current.x * this.pixelSize, current.y * this.pixelSize, this.pixelSize, this.pixelSize);
      }
    }

  }

  //unfortunately the has method of ts sets does not use the equals method
  private setHasPosition(set: Set<Position>, position: Position): boolean {
    for (const position1 of set) {
      if (position1.equals(position))
        return true;
    }
    return false;
  }
}
