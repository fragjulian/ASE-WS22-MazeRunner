package com.example.mazerunner;

import java.util.LinkedList;
import java.util.List;

public class SquareDistance implements DistanceMetric {
    @Override
    public double getDistance(Position a, Position b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    @Override
    public List<Position> getNeighbouringPixels(Position position, int radius, int width, int height) {
        if (radius <= 0)
            return new LinkedList<>();
        List<Position> result = new LinkedList();
        for (int x = position.getX() - 1; x <= position.getX() + 1; x++) {
            for (int y = position.getY() - 1; y <= position.getY() + 1; y++) {
                if ((x == position.getX() && y == position.getY()) || x < 0 || x >= width || y < 0 || y >= height || !(x == position.getX() || y == position.getY()))
                    continue;
                result.add(new Position(x, y));
            }
        }

        List<Position> recursiveResult = new LinkedList();
        for (Position resultPosition : result) {
            recursiveResult.addAll(getNeighbouringPixels(resultPosition, radius - 1, width, height).stream().filter(p -> !recursiveResult.contains(p)).toList());

        }
        result.addAll(recursiveResult.stream().filter(p -> !result.contains(p)).toList());
        return result;
    }
}
