package com.example.mazerunner;

import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

public class Maze {
    //image type the targeted colours are in
    private final int pathColour;
    private final Heuristic heuristic;
    private final WallDetector wallDetector;
    private final SearchStrategy searchStrategy;
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
     * @param imageType     the ImageType of the final image and of pathColour and wallDetector
     * @param pathColour    the colour in which you want the path to be painted in ColourSpace of ImageType
     */
    public Maze(BufferedImage bufferedImage, Heuristic heuristic, WallDetector wallDetector, SearchStrategy searchStrategy, int imageType, int pathColour) {
        this.heuristic = heuristic;
        this.wallDetector = wallDetector;
        this.searchStrategy = searchStrategy;
        this.pathColour = pathColour;
        //check if image is already in the correct colour space. If not, convert it
        if (bufferedImage.getType() == imageType)
            this.bufferedImage = bufferedImage;
        else {
            BufferedImage rgbImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), imageType);
            ColorConvertOp op = new ColorConvertOp(null);
            op.filter(bufferedImage, rgbImage);
            this.bufferedImage = rgbImage;
        }
    }

    /**
     * find a path out of the maze
     * @return a bufferedImage of the maze with the path from start to goal included
     */
    public BufferedImage solveMaze() {
        walls = wallDetector.detectWall(bufferedImage);
        //todo set targets
        goal = new Position(bufferedImage.getWidth() - 2, bufferedImage.getHeight() - 2);
        //todo set start
        start = new Position(0, 0);
        heuristic.calculateHeuristic(bufferedImage.getWidth(), bufferedImage.getHeight(), start, goal, walls);
        searchStrategy.calculateShortestPath(bufferedImage, heuristic, walls, start, goal, pathColour);
        return bufferedImage;
    }
}
