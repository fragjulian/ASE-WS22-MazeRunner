package com.example.mazerunner;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Position) {
            Position op = (Position) o;
            return op.x == this.x && op.y == this.y;

        }
        return false;
    }
}
