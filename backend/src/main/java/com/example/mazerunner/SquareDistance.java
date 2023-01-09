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
        List<Position> result = new LinkedList<>();
        for (int x = position.getX() - 1; x <= position.getX() + 1; x++) {
            for (int y = position.getY() - 1; y <= position.getY() + 1; y++) {
                Position neighbour = new Position(x, y);
                if (!isNeighbouringPosition(position, neighbour, width, height))
                    continue;
                result.add(neighbour);
            }
        }

        List<Position> recursiveResult = new LinkedList<>();
        for (Position resultPosition : result) {
            recursiveResult.addAll(getNeighbouringPixels(resultPosition, radius - 1, width, height).stream().filter(p -> !recursiveResult.contains(p) && !p.equals(position)).toList());

        }
        result.addAll(recursiveResult.stream().filter(p -> !result.contains(p)).toList());
        return result;
    }

    /**
     * check if neighbour is a valid neighbouring pixel of currentPosition
     *
     * @param currentPosition the position in the middle of the neighbours
     * @param neighbour       the position which you want to see if it is a neighbour
     * @param width           max width of the image
     * @param height          max height of the mase
     * @return true if it is valid, false otherwise
     */
    private boolean isNeighbouringPosition(Position currentPosition, Position neighbour, int width, int height) {
        return (!((neighbour.getX() == currentPosition.getX() && neighbour.getY() == currentPosition.getY()) ||
                neighbour.getX() < 0 || neighbour.getX() >= width || neighbour.getY() < 0 || neighbour.getY() >= height ||
                !(neighbour.getX() == currentPosition.getX() || neighbour.getY() == currentPosition.getY())) &&
                this.getDistance(currentPosition, neighbour) == 1);

    }
}
