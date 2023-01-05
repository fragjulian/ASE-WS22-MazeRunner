package com.example.mazerunner;

public class JsonWallDetector implements WallDetector {
    private boolean[][] walls;
    private Position[] positionsWalls;
    private int safetyDistance;
    private Position[] obstacles;
    private DistanceMetric distanceMetric;

    public JsonWallDetector() {
    }

    public void setWalls(Position[] positions) {
        this.positionsWalls = positions;
        /*this.walls=new boolean[100][100];
        for (Position position:positions) {
            this.walls[position.getX()][position.getY()]=true;
        }*/
    }

    public void setSafetyDistance(int safetyDistance) {
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
        //todo think about not using the bufferedImage here
        boolean walls[][] = new boolean[maze.getWidth()][maze.getHeight()];
        //walls
        for (Position wall : this.positionsWalls) {
            walls[wall.getX()][wall.getY()] = true;
        }
        //obstacles
        for (Position obstacle : this.obstacles) {
            walls[obstacle.getX()][obstacle.getY()] = true;
            for (Position neighbour : maze.getDistanceMetric().getNeighbouringPixels(obstacle, this.safetyDistance, maze.getWidth(), maze.getHeight())) {
                walls[obstacle.getX()][obstacle.getY()] = true;
            }
        }
        return walls;

    }
}
