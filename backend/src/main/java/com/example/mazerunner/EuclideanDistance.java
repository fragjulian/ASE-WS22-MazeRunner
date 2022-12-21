package com.example.mazerunner;

import java.util.ArrayList;
import java.util.List;

public class EuclideanDistance implements DistanceMetric {
    @Override
    public double getDistance(Position a, Position b) {
        return Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2));
    }

    @Override
    public List<Position> getNeighbouringPixels(Position position, int radius, int width, int height) {
        List<Position> result = new ArrayList<>((int) Math.pow(radius * 2 + 1, 2));
        for (int xNeighbour = position.getX() - radius; xNeighbour <= position.getX() + radius; xNeighbour++) {
            for (int yNeighbour = position.getY() - radius; yNeighbour <= position.getY() + radius; yNeighbour++) {
                if (xNeighbour < 0 || xNeighbour >= width || yNeighbour < 0 || yNeighbour >= height)
                    continue;
                result.add(new Position(xNeighbour, yNeighbour));
            }
        }
        return result;
    }
}
