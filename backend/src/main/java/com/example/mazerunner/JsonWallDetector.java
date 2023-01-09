package com.example.mazerunner;

/**
 * This class (setWalls and setObstacles) is used by spring when accepting the post-request with the positions of obstacles and walls in json form. The other methods in the backend can then use this class to retrieve this information without any additional changes.
 */
public class JsonWallDetector implements WallDetector {
    private Position[] walls;
    private int safetyDistance;
    private Position[] obstacles;
    private DistanceMetric distanceMetric;

    public JsonWallDetector() {
    }

    public void setWalls(Position[] positions) {
        this.walls = positions;
    }

    public void setSafetyDistance(int safetyDistance) {
        if (safetyDistance < 1 || safetyDistance > 20)
            throw new IllegalArgumentException("safety distance must be between 1 and 20");
        this.safetyDistance = safetyDistance;
    }

    public void setObstacles(Position[] positions) {
        this.obstacles = positions;
    }

    public void setDistanceMetric(DistanceMetric distanceMetric) {
        this.distanceMetric = distanceMetric;
    }

    @Override
    public boolean[][] detectWall(Maze maze) {
        boolean walls[][] = new boolean[maze.getWidth()][maze.getHeight()];
        //walls
        for (Position wall : this.walls) {
            walls[wall.getX()][wall.getY()] = true;
        }
        //obstacles
        for (Position obstacle : this.obstacles) {
            walls[obstacle.getX()][obstacle.getY()] = true;
            for (Position neighbour : maze.getDistanceMetric().getNeighbouringPixels(obstacle, this.safetyDistance, maze.getWidth(), maze.getHeight())) {
                walls[neighbour.getX()][neighbour.getY()] = true;
            }
        }
        return walls;

    }
}
