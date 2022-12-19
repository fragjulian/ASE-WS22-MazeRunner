package com.example.mazerunner;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class DepthFirst implements SearchStrategy {
    public void calculateShortestPath(BufferedImage bufferedImage, Heuristic heuristic, boolean[][] walls, Position start, Position goal, int pathColor) {
        Position current = start;
        List<Position> alreadyVisited = new LinkedList<>();//todo implement backtracking
        while (!current.equals(goal)) {
            bufferedImage.setRGB(current.getX(), current.getY(), pathColor);
            Position cheapestNeighbour = null;
            for (int x = current.getX() - 1; x <= current.getX() + 1; x++) {
                for (int y = current.getY() - 1; y <= current.getY() + 1; y++) {
                    Position currentNeighbour = new Position(x, y);
                    //only iterate over neighbours or valid pixels
                    if ((x == current.getX() && y == current.getY()) || x < 0 || x >= bufferedImage.getWidth() || y < 0 || y >= bufferedImage.getHeight() || walls[x][y] || alreadyVisited.contains(currentNeighbour))
                        continue;
                    if (cheapestNeighbour == null || heuristic.getHeuristic(cheapestNeighbour) > heuristic.getHeuristic(currentNeighbour) && !alreadyVisited.contains(cheapestNeighbour))
                        cheapestNeighbour = currentNeighbour;
                }
            }
            //todo backtrack if we did not find a next cheapest neighbour
            alreadyVisited.add(current);
            current = cheapestNeighbour;
        }
    }
}
