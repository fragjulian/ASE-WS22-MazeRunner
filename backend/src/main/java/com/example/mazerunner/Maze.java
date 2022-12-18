package com.example.mazerunner;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class Maze {
    // pixel value that identifies it as part of a wall
    private static final int WALL = -16777216;
    private static final int OBSTACLE = -2368549;

    private final BufferedImage bufferedImage;
    private Position goal;
    private Position start;
    private boolean[][] walls;
    private final DistanceMetric distanceMetric = new EuclideanDistance();
    private final Heuristic heuristic = new RealDistanceHeuristic(distanceMetric);
    WallDetector wallDetector = new ColourWallDetector(WALL);

    public Maze(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public BufferedImage solveMaze() {
        walls = wallDetector.detectWall(bufferedImage);
        //todo set targets
        goal = new Position(bufferedImage.getHeight() - 2, bufferedImage.getWidth() - 2);
        //todo set start
        start = new Position(0, 0);
        heuristic.calculateHeuristic(bufferedImage, start, goal, walls);
        calculateShortestPath();
        return bufferedImage;
    }

    private void calculateShortestPath() {
        Position current = start;
        List<Position> alreadyVisited = new LinkedList<>();//todo implement backtracking
        while (!current.equals(goal)) {
            bufferedImage.setRGB(current.x, current.y, -2368549);
            Position cheapestNeighbour = null;
            for (int x = current.x - 1; x <= current.x + 1; x++) {
                for (int y = current.y - 1; y <= current.y + 1; y++) {
                    //only iterate over neighbours or valid pixels
                    if ((x == current.x && y == current.y) || x < 0 || x >= bufferedImage.getWidth() || y < 0 || y >= bufferedImage.getHeight() || walls[x][y] || alreadyVisited.contains(new Position(x, y)))
                        continue;
                    if (cheapestNeighbour == null || heuristic.getHeuristic(cheapestNeighbour) > heuristic.getHeuristic(new Position(x, y)) && !alreadyVisited.contains(cheapestNeighbour))
                        cheapestNeighbour = new Position(x, y);
                }
            }
            //todo backtrack if we did not find a next cheapest neighbour
            alreadyVisited.add(current);
            current = cheapestNeighbour;
        }
    }
}
