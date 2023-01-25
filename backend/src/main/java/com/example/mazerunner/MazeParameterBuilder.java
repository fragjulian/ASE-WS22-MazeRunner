package com.example.mazerunner;

import java.awt.image.BufferedImage;

public class MazeParameterBuilder {
    private MazeUtilsFactory mazeUtilsFactory = MazeUtilsFactory.getMazeUtilsFactory();

    private Heuristic heuristic;
    private WallDetector wallDetector;
    private int safetyDistance;
    private SearchStrategy searchStrategy;
    private int imageType;
    private int pathColor;
    private int backgroundColor;
    private int startColor;
    private int goalColor;
    private Position startPos;
    private Position goalPos;
    private DistanceMetric distanceMetric;

    public MazeParameterBuilder setWallDetector(String wallDetectorParameter, String wallColorParameter, String obstacleColorParameter, Integer safetyDistance) {
        this.wallDetector = mazeUtilsFactory.getWallDetector(wallDetectorParameter, wallColorParameter, obstacleColorParameter, safetyDistance, this.distanceMetric);
        return this;
    }

    public MazeParameterBuilder setDistanceMetric(String distanceMetricParameter) {
        this.distanceMetric = mazeUtilsFactory.getDistanceMetric(distanceMetricParameter);
        return this;
    }

    public MazeParameterBuilder setHeuristic(String heuristicParameter) {
        this.heuristic = mazeUtilsFactory.getHeuristic(heuristicParameter, this.distanceMetric);
        return this;
    }

    public MazeParameterBuilder setSearchStrategy(String searchStrategyParameter) {
        this.searchStrategy = mazeUtilsFactory.getSearchStrategy(searchStrategyParameter);
        return this;
    }

    public MazeParameterBuilder setImageType(int imageType) {
        this.imageType = imageType;
        return this;
    }

    public MazeParameterBuilder setPathColor(int pathColor) {
        this.pathColor = pathColor;
        return this;
    }

    public MazeParameterBuilder setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public MazeParameterBuilder setDistanceMetric(DistanceMetric distanceMetric) {
        this.distanceMetric = distanceMetric;
        return this;
    }

    public MazeParameterBuilder setStartColor(String startColor) {
        this.startColor = mazeUtilsFactory.getStartColor(startColor);
        return this;
    }

    public MazeParameterBuilder setGoalColor(String goalColor) {
        this.goalColor = mazeUtilsFactory.getGoalColor(goalColor);
        return this;
    }

    public MazeParameterBuilder setStartPosition(Position startPosition) {
        this.startPos = startPosition;
        return this;
    }

    public MazeParameterBuilder setGoalPosition(Position goalPosition) {
        this.goalPos = goalPosition;
        return this;
    }

    public MazeParameterBuilder setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
        return this;
    }

    public MazeParameterBuilder setWallDetector(WallDetector wallDetector) {
        this.wallDetector = wallDetector;
        if (wallDetector instanceof JsonWallDetector) {
            ((JsonWallDetector) wallDetector).setDistanceMetric(this.distanceMetric);
            if (this.safetyDistance != 0)
                ((JsonWallDetector) wallDetector).setSafetyDistance(this.safetyDistance);
        }

        return this;
    }

    public MazeParameterBuilder setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
        return this;
    }

    public Maze build(BufferedImage bufferedImage) {
        return new Maze(bufferedImage, heuristic, wallDetector, searchStrategy, this.imageType, this.pathColor, this.backgroundColor, distanceMetric, startColor, goalColor);
    }

    public Maze build(int sizeX, int sizeY) {
        return new Maze(sizeX, sizeY, heuristic, wallDetector, searchStrategy, distanceMetric, startPos, goalPos);
    }

    public MazeParameterBuilder setSafetyDistance(int safetyDistance) {
        this.safetyDistance = safetyDistance;
        if (this.wallDetector != null && this.wallDetector instanceof JsonWallDetector)
            ((JsonWallDetector) wallDetector).setSafetyDistance(this.safetyDistance);
        return this;
    }
}
