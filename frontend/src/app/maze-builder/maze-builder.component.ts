import {Component} from '@angular/core';
import {MazeBuilderAutoSolveService} from "../maze-builder-auto-solve.service";
import {Position} from "../Position";


@Component({
  selector: 'app-maze-canvas',
  templateUrl: './maze-builder.component.html',
  styleUrls: ['./maze-builder.component.css']
})
export class MazeBuilderComponent {

  private readonly initialBrushColor = 'black';
  private readonly initialBrushColors = ['black', 'gray', 'green', 'purple', 'blue'];

  // usually the left mouse button
  private readonly primaryMouseButton = 1;

  private readonly mazeDefaultFileName = 'my-maze.png';
  canvas: HTMLCanvasElement | undefined;
  context: CanvasRenderingContext2D | undefined;
  colorPicker: HTMLElement | undefined;
  wallColor: HTMLSpanElement | undefined;
  obstacleColor: HTMLSpanElement | undefined;
  private walls = new Set<Position>();
  brushColor = this.initialBrushColor;
  private startPosition: Position | undefined;
  private goalPosition: Position | undefined;
  pixelSize = 15;
  // Current x-coordinate of the cursor on the canvas
  cursorPosX = 0;
  // Current y-coordinate of the cursor on the canvas
  cursorPosY = 0;
  private currentPath: Position[] = [];
  brushColors = this.initialBrushColors;
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
    this.wallColor = document.getElementById('wallColor') as HTMLSpanElement;
    this.obstacleColor = document.getElementById('obstacleColor') as HTMLSpanElement;
    this.canvas.addEventListener('click', this.handleClick.bind(this));
    this.canvas.addEventListener('mousemove', this.handleMouseMove.bind(this));
    this.canvas.addEventListener('mousedown', this.handleMouseDown.bind(this));
    this.canvas.addEventListener('mouseup', this.handleMouseUp.bind(this));
  }

  saveBrushColor() {
    if (this.selectedBrush == "wall")
      this.brushColors[0] = this.brushColor;
    if (this.selectedBrush == "obstacles")
      this.brushColors[1] = this.brushColor;
    if (this.selectedBrush == "start")
      this.brushColors[2] = this.brushColor;
    if (this.selectedBrush == "goal")
      this.brushColors[3] = this.brushColor;

  }

  restoreBrushColor() {
    if (this.selectedBrush == "wall")
      this.brushColor = this.brushColors[0];
    if (this.selectedBrush == "obstacle")
      this.brushColor = this.brushColors[1]
    if (this.selectedBrush == "start")
      this.brushColor = this.brushColors[2]
    if (this.selectedBrush == "goal")
      this.brushColor = this.brushColors[3]
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

  // Clears the entire canvas
  clearMaze() {
    const width = this.canvas!.width;
    const height = this.canvas!.height;
    // Clear the canvas
    this.context!.clearRect(0, 0, width, height);
    this.walls = new Set<Position>();
    this.obstacles = new Set<Position>();
    this.startPosition = undefined;
    this.goalPosition = undefined;
  }

  getSolvedPath() {
    if (this.startPosition == undefined || this.goalPosition == undefined) {
      console.log("No start or goal position set");
      return;
    }

    console.log("getting solved path");
    //var returnObject={walls:this.walls}
    let returnObject = {
      walls: Array.from(this.walls.values()),
      obstacles: Array.from(this.obstacles.values())
    }
    this.mazeBuilderAutoSolve.downloadSolutionPath(this.canvas!.width / this.pixelSize, this.canvas!.height / this.pixelSize, this.walls, this.obstacles, this.startPosition.x, this.startPosition.y, this.goalPosition.x, this.goalPosition.y)
      .subscribe((response: any) => //todo use angular to directly convert this to array and not use any
        this.drawPath(response.body.path)
      );
  }

  drawPath(positions: Position[]) {
    this.clearCurrentPath();
    this.currentPath = [];
    for (let path of positions) {
      this.currentPath.push(new Position(path.x, path.y));
      this.drawPixel(path.x, path.y, 'red')
    }
  }

  openColorPickerDialog() {
    this.colorPicker!.click();
  }

  private drawStartAtCurrentMousePosition(posX: number, posY: number) {
    if (this.positionIncludedInSet(this.walls, new Position(posX, posY))) return;
    if (this.positionIncludedInSet(this.obstacles, new Position(posX, posY))) return;
    if (this.goalPosition?.equals(new Position(posX, posY))) return;

    if (this.startPosition != undefined) {
      this.context!.clearRect(this.startPosition.x * this.pixelSize, this.startPosition.y * this.pixelSize, this.pixelSize, this.pixelSize);
    }
    this.cursorPosX = posX;
    this.cursorPosY = posY;
    this.drawPixel(this.cursorPosX, this.cursorPosY, this.brushColors[2]);
    this.startPosition = new Position(this.cursorPosX, this.cursorPosY);
  }

  private drawGoalAtCurrentMousePosition(posX: number, posY: number) {
    if (this.positionIncludedInSet(this.walls, new Position(posX, posY))) return;
    if (this.positionIncludedInSet(this.obstacles, new Position(posX, posY))) return;
    if (this.startPosition?.equals(new Position(posX, posY))) return;

    if (this.goalPosition != undefined) {
      this.context!.clearRect(this.goalPosition.x * this.pixelSize, this.goalPosition.y * this.pixelSize, this.pixelSize, this.pixelSize);
    }
    this.cursorPosX = posX;
    this.cursorPosY = posY;
    this.drawPixel(this.cursorPosX, this.cursorPosY, this.brushColors[3]);
    this.goalPosition = new Position(this.cursorPosX, this.cursorPosY);
  }

  private drawPixel(xCoord: number, yCoord: number, color: string) {
    this.context!.fillStyle = color;
    const startX = xCoord * this.pixelSize;
    const startY = yCoord * this.pixelSize;
    this.context!.fillRect(startX, startY, this.pixelSize, this.pixelSize);
  }

  private clearPixel(xCoord: number, yCoord: number) {
    let pos = new Position(xCoord, yCoord);
    if (this.startPosition?.equals(pos) || this.goalPosition?.equals(pos)) {
      return;
    }
    this.context!.clearRect(xCoord * this.pixelSize, yCoord * this.pixelSize, this.pixelSize, this.pixelSize);

    this.deletePositionsInSet(this.walls, pos);
    this.deletePositionsInSet(this.obstacles, pos);
  }

  // Exports the current state of the canvas as an image file
  exportMazeAsImage() {

    const dataUrl = this.canvas!.toDataURL();

    const link = document.createElement('a');

    link.href = dataUrl;
    // Set the download attribute of the link to the default file name for the maze image
    link.download = this.mazeDefaultFileName;
    link.click();
  }

  // Draws a border around the entire canvas
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

    if (this.selectedBrush == "start") {
      this.drawStartAtCurrentMousePosition(this.cursorPosX, this.cursorPosY);
    } else if (this.selectedBrush == "goal") {
      this.drawGoalAtCurrentMousePosition(this.cursorPosX, this.cursorPosY);
    } else if (this.selectedBrush == "delete") {
      this.clearPixel(this.cursorPosX, this.cursorPosY);
    } else {
      //this.restoreBrushColor();
      this.drawPixel(this.cursorPosX, this.cursorPosY, this.brushColor);
      if (this.selectedBrush == "wall")
        this.walls.add(new Position(this.cursorPosX, this.cursorPosY));
      if (this.selectedBrush == "obstacle")
        this.obstacles.add(new Position(this.cursorPosX, this.cursorPosY));
    }
  }

  private clearCurrentPath() {
    for (let current of this.currentPath) {
      if (this.positionIncludedInSet(this.walls, current)) {//only clear pixels where no wall has been drawn notice: has not correctly returning
        this.drawPixel(current.x * this.pixelSize, current.y * this.pixelSize, this.brushColors[0]);//redraw wall
      } else if (this.positionIncludedInSet(this.obstacles, current))
        this.drawPixel(current.x * this.pixelSize, current.y * this.pixelSize, this.brushColors[1]);//redraw wall
      else {
        this.context!.clearRect(current.x * this.pixelSize, current.y * this.pixelSize, this.pixelSize, this.pixelSize);
      }
    }

  }

  //unfortunately the has method of ts sets does not use the equals method
  private positionIncludedInSet(set: Set<Position>, position: Position): boolean {
    for (const position1 of set) {
      if (position1.equals(position))
        return true;
    }
    return false;
  }

  private deletePositionsInSet(positions: Set<Position>, position: Position): Set<Position> {
    positions.forEach((setPos) => {
      if (setPos.equals(setPos)) {
        positions.delete(setPos);
      }
    });
    return positions;
  }
}
