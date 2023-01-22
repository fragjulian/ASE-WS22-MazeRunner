package at.stulpinger.ase;

public record Line(Canvas.Pixel start, Direction direction, int steps) {
  public enum Direction {
    LEFT,
    LEFT_UP,
    UP,
    RIGHT_UP,
    RIGHT,
    RIGHT_BOTTOM,
    BOTTOM,
    LEFT_BOTTOM
  }

}