package com.example.mazerunner;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

public class Maze {
    //image type the targeted colors are in
    private final int pathColor;//todo move out of constructor
    private final int startColor;
    private final int goalColor;
    private final Heuristic heuristic;
    private final WallDetector wallDetector;
    private final SearchStrategy searchStrategy;
    private final int sizeX;
    private final DistanceMetric distanceMetric;
    private Position goal;
    private final int sizeY;
    private BufferedImage bufferedImage;
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
        setBufferedImage(bufferedImage, imageType, backgroundColor);
        sizeX = bufferedImage.getWidth();
        sizeY = bufferedImage.getHeight();
    }

    public Maze(int sizeX, int sizeY, Heuristic heuristic, WallDetector wallDetector, SearchStrategy searchStrategy, DistanceMetric distanceMetric, int startColor, int goalColor) {
        this.sizeY = sizeY;
        this.sizeX = sizeX;
        this.wallDetector = wallDetector;
        this.searchStrategy = searchStrategy;
        this.distanceMetric = distanceMetric;
        this.heuristic = heuristic;
        this.pathColor = 0;//todo remove
        this.startColor = startColor;
        this.goalColor = goalColor;
    }

    private void setBufferedImage(BufferedImage bufferedImage, int imageType, int backgroundColor) {
        //check if image is already in the correct color space. If not, convert it
        if (bufferedImage.getType() == imageType)
            this.bufferedImage = bufferedImage;
        else {
            BufferedImage rgbImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), imageType);//todo nicely use parameters from beginning
            for (int x = 0; x < bufferedImage.getWidth(); x++)
                for (int y = 0; y < bufferedImage.getHeight(); y++)
                    if (bufferedImage.getRGB(x, y) == COLOR_TRANSPARENT.getRGB())
                        rgbImage.setRGB(x, y, backgroundColor);

            ColorConvertOp op = new ColorConvertOp(null);
            op.filter(bufferedImage, rgbImage);
            this.bufferedImage = rgbImage;
        }
    }

    public BufferedImage getBufferedImage() {
        if (bufferedImage == null)
            bufferedImage = new BufferedImage(this.sizeX, this.sizeY, BufferedImage.TYPE_INT_RGB);
        return bufferedImage;
    }

    /**
     * find a path out of the maze
     *
     * @return a bufferedImage of the maze with the path from start to goal included
     */
    public BufferedImage getSolvedMaze() {

        bufferedImage = getSolutionPath().drawPath(this.getBufferedImage(), pathColor);
        return bufferedImage;
    }

    public Path getSolutionPath() {
        walls = wallDetector.detectWall(this);
        goal = PositionDetectorColor.getPositionByColor(bufferedImage, this.goalColor);
        start = PositionDetectorColor.getPositionByColor(bufferedImage, this.startColor);
        heuristic.calculateHeuristic(sizeX, sizeY, start, goal, walls);
        return searchStrategy.calculateShortestPath(this);
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
        return this.sizeX;
    }

    public int getHeight() {
        return this.sizeY;
    }

    public DistanceMetric getDistanceMetric() {
        return distanceMetric;
    }
}
