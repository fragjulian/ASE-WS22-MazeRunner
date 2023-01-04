package com.example.mazerunner;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

public class Maze {
    //image type the targeted colors are in
    private final int pathColor;
    private final int startColor;
    private final int goalColor;
    private final Heuristic heuristic;
    private final WallDetector wallDetector;
    private final SearchStrategy searchStrategy;
    private final BufferedImage bufferedImage;
    private final DistanceMetric distanceMetric;
    private Position goal;
    private Position start;
    private boolean[][] walls;
    private static final Color COLOR_TRANSPARENT = new Color(0, 0, 0, 0);

    /**
     * This represents a solvable maze
     *
     * @param bufferedImage  image of the maze in any ImageType
     * @param heuristic      any heuristic to help with finding a solution
     * @param wallDetector   any detector to detect walls and obstacles
     * @param imageType      the ImageType of the final image and of pathColor and wallDetector
     * @param pathColor      the color in which you want the path to be painted in ColorSpace of ImageType
     * @param distanceMetric the metric how distance between two positions is calculated
     * @param startColor     the color of the start pixel to solve the maze
     * @param goalColor      the color of the goal pixel to solve the maze
     */
    public Maze(BufferedImage bufferedImage, Heuristic heuristic, WallDetector wallDetector, SearchStrategy searchStrategy, int imageType, int pathColor, int backgroundColor, DistanceMetric distanceMetric, int startColor, int goalColor) {
        this.heuristic = heuristic;
        this.startColor = startColor;
        this.goalColor = goalColor;
        this.wallDetector = wallDetector;
        this.searchStrategy = searchStrategy;
        this.pathColor = pathColor;
        this.distanceMetric = distanceMetric;
        //check if image is already in the correct color space. If not, convert it
        if (bufferedImage.getType() == imageType)
            this.bufferedImage = bufferedImage;
        else {
            BufferedImage rgbImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), imageType);
            for (int x = 0; x < bufferedImage.getWidth(); x++)
                for (int y = 0; y < bufferedImage.getHeight(); y++)
                    if (bufferedImage.getRGB(x, y) == COLOR_TRANSPARENT.getRGB())
                        rgbImage.setRGB(x, y, backgroundColor);

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
        start = PositionDetectorColor.getPositionByColor(bufferedImage, this.startColor);
        goal = PositionDetectorColor.getPositionByColor(bufferedImage, this.goalColor);
        heuristic.calculateHeuristic(bufferedImage.getWidth(), bufferedImage.getHeight(), start, goal, walls);
        searchStrategy.calculateShortestPath(this);
        return bufferedImage;
    }

    public int getPathColor() {
        return pathColor;
    }

    public void paintOnMaze(Position position, int color) {
        bufferedImage.setRGB(position.getX(), position.getY(), color);
    }

    public Position getGoal() {
        return goal;
    }

    public Position getStart() {
        return start;
    }

    public boolean getWall(Position position) {
        return walls[position.getX()][position.getY()];
    }

    public double getHeuristic(Position position) {
        return heuristic.getHeuristic(position);
    }

    public int getWidth() {
        return bufferedImage.getWidth();
    }

    public int getHeight() {
        return bufferedImage.getHeight();
    }

    public DistanceMetric getDistanceMetric() {
        return distanceMetric;
    }
}
