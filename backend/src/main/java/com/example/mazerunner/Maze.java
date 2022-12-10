package com.example.mazerunner;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Maze {
    // pixel value that identifies it as part of a wall
    private static final int WALL = -16777216;
    private static final int OBSTACLE = -2368549;

    // pixel value of the target point
    private static final int TARGET = 120;

    private BufferedImage bufferedImage;
    private Position goal;
    private Position start;
    private double[][] heuristic;
    private static final int WALL_MARKER = -1;
    // pixel value of the staring point
    private static final int START = 60;
    private boolean[][] walls;
    private DistanceMetric distanceMetric = new EuclideanDistance();

    WallDetector wallDetector = new ColourWallDetector(WALL);

    public Maze(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        heuristic = new double[bufferedImage.getWidth()][bufferedImage.getHeight()];
    }

    public BufferedImage solveMaze() {
        walls = wallDetector.detectWall(bufferedImage);
        initHeuristic();
        //todo set targets
        goal = new Position(bufferedImage.getHeight() - 2, bufferedImage.getWidth() - 2);
        //todo set start
        start = new Position(0, 0);
        calculateHeuristic();
        calculateShortestPath();
        return bufferedImage;

    }

    private void initHeuristic() {

        //init distance map
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {

                if (walls[x][y] == true) {
                    // mark "no-go" area
                    heuristic[x][y] = WALL_MARKER;
                } else {
                    if (bufferedImage.getRGB(x, y) == START) {
                        // remember start pixel
                        start = new Position(x, y);
                    }

                    // init. every other pixel with infinity distance
                    heuristic[x][y] = Double.POSITIVE_INFINITY;
                }
            }
        }

    }

    private void calculateHeuristic() {
        //todo include error checking if we have calculated the distance to all valid pixels
        Queue<Position> processingStack = new LinkedBlockingQueue<>();
        heuristic[goal.x][goal.y] = 0;//per definition the distance to the goal is 0
        processingStack.add(goal);
        while (!processingStack.isEmpty()) {
            Position current = processingStack.poll();
            for (int x = current.x - 1; x <= current.x + 1; x++) {
                for (int y = current.y - 1; y <= current.y + 1; y++) {
                    //only iterate over neighbours or valid pixels
                    if ((x == current.x && y == current.y) || x < 0 || x >= bufferedImage.getWidth() || y < 0 || y >= bufferedImage.getHeight() || walls[x][y])
                        continue;

                    double newHeuristic = heuristic[current.x][current.y] + distanceMetric.getDistance(current, new Position(x, y));
                    if (newHeuristic < heuristic[x][y]) {
                        //if our distance changed we also need to re-calculate the distance of all our neighbours.
                        heuristic[x][y] = newHeuristic;
                        processingStack.add(new Position(x, y));
                    }


                }
            }


        }
    }

    private void calculateShortestPath() {
        Position current = start;
        List alreadyVisited = new LinkedList<Position>();//todo implement backtracking
        while (!current.equals(goal)) {
            bufferedImage.setRGB(current.x, current.y, -2368549);
            Position cheapestNeighbour = null;
            for (int x = current.x - 1; x <= current.x + 1; x++) {
                for (int y = current.y - 1; y <= current.y + 1; y++) {
                    //only iterate over neighbours or valid pixels
                    if ((x == current.x && y == current.y) || x < 0 || x >= bufferedImage.getWidth() || y < 0 || y >= bufferedImage.getHeight() || walls[x][y] || alreadyVisited.contains(new Position(x, y)))
                        continue;
                    if ((cheapestNeighbour == null || heuristic[cheapestNeighbour.x][cheapestNeighbour.y] > heuristic[x][y]) && !alreadyVisited.contains(cheapestNeighbour))
                        cheapestNeighbour = new Position(x, y);
                }
            }
            //todo backtrack if we did not find a next cheapest neighbour
            alreadyVisited.add(current);
            current = cheapestNeighbour;


        }


    }

}
