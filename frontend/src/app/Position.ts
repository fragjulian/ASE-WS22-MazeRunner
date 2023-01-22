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
