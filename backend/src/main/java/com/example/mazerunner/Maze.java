package com.example.mazerunner;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.util.LinkedList;
import java.util.List;

public class Maze {
    //image type the targeted colours are in
    private static final int IMAGE_TYPE = BufferedImage.TYPE_INT_RGB;

    private static final int WALL = new Color(0, 0, 0).getRGB();

    private static final int PATH = new Color(255, 0, 0).getRGB();

    private final BufferedImage bufferedImage;
    private Position goal;
    private Position start;
    private boolean[][] walls;
    private final DistanceMetric distanceMetric = new EuclideanDistance();
    private final Heuristic heuristic = new RealDistanceHeuristic(distanceMetric);
    WallDetector wallDetector = new ColourWallDetector(WALL);

    public Maze(BufferedImage bufferedImage) {
        //check if image is already in the correct colour space. If not, convert it
        if (bufferedImage.getType() == IMAGE_TYPE)
            this.bufferedImage = bufferedImage;
        else {
            BufferedImage rgbImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), IMAGE_TYPE);
            ColorConvertOp op = new ColorConvertOp(null);
            op.filter(bufferedImage, rgbImage);
            this.bufferedImage = rgbImage;
        }
    }

    /**
     * find a path out of the maze
     *
     * @return a bufferedImage of the maze with the path from start to goal included
     */
    public BufferedImage solveMaze() {
        walls = wallDetector.detectWall(bufferedImage);
        //todo set targets
        goal = new Position(bufferedImage.getHeight() - 2, bufferedImage.getWidth() - 2);
        //todo set start
        start = new Position(0, 0);
        heuristic.calculateHeuristic(bufferedImage.getWidth(), bufferedImage.getHeight(), start, goal, walls);
        calculateShortestPath();
        return bufferedImage;
    }

    private void calculateShortestPath() {
        Position current = start;
        List<Position> alreadyVisited = new LinkedList<>();//todo implement backtracking
        while (!current.equals(goal)) {
            bufferedImage.setRGB(current.getX(), current.getY(), PATH);
            Position cheapestNeighbour = null;
            for (int x = current.getX() - 1; x <= current.getX() + 1; x++) {
                for (int y = current.getY() - 1; y <= current.getY() + 1; y++) {
                    //only iterate over neighbours or valid pixels
                    if ((x == current.getX() && y == current.getY()) || x < 0 || x >= bufferedImage.getWidth() || y < 0 || y >= bufferedImage.getHeight() || walls[x][y] || alreadyVisited.contains(new Position(x, y)))
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
