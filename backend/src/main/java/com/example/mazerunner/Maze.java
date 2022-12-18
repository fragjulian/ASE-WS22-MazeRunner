package com.example.mazerunner;

import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.util.LinkedList;
import java.util.List;

public class Maze {
    //image type the targeted colours are in


    private final int PATH_COLOUR;

    private final Heuristic heuristic;
    private final WallDetector wallDetector;

    private final BufferedImage bufferedImage;
    private Position goal;
    private Position start;
    private boolean[][] walls;


    /**
     * This represents a solvable maze
     *
     * @param bufferedImage image of the maze in any ImageType
     * @param heuristic     any heuristic to help with finding a solution
     * @param wallDetector  any detector to detect walls and obstacles
     * @param IMAGE_TYPE    the ImageType of the final image and of PATH_COLOUR and wallDetector
     * @param PATH_COLOUR   the colour in which you want the path to be painted in ColourSpace of ImageType
     */
    public Maze(BufferedImage bufferedImage, Heuristic heuristic, WallDetector wallDetector, int IMAGE_TYPE, int PATH_COLOUR) {
        this.heuristic = heuristic;
        this.wallDetector = wallDetector;
        this.PATH_COLOUR = PATH_COLOUR;
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
            bufferedImage.setRGB(current.getX(), current.getY(), PATH_COLOUR);
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
