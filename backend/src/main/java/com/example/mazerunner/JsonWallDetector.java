package com.example.mazerunner;

public class JsonWallDetector implements WallDetector {
    private Position[] walls;
    private int safetyDistance;
    private Position[] obstacles;
    private DistanceMetric distanceMetric;

    public JsonWallDetector() {
    }

    public void setWalls(Position[] positions) {
        this.walls = positions;
        /*this.walls=new boolean[100][100];
        for (Position position:positions) {
            this.walls[position.getX()][position.getY()]=true;
        }*/
    }

    public void setSafetyDistance(int safetyDistance) {
        if (safetyDistance < 1 || safetyDistance > 20)
            throw new IllegalArgumentException("safety distance must be between 1 and 20");
        this.safetyDistance = safetyDistance;
    }

    public void setObstacles(Position[] positions) {
        this.obstacles = positions;
       /* for (Position position:positions) {
            this.obstacles[position.getX()][position.getY()]=true;
        }*/
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
