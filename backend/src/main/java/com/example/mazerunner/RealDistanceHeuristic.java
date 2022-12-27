package com.example.mazerunner;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class RealDistanceHeuristic implements Heuristic {
    private final DistanceMetric distanceMetric;
    private double[][] heuristic;

    public RealDistanceHeuristic(DistanceMetric distanceMetric) {
        this.distanceMetric = distanceMetric;
    }

    private void initHeuristic(int width, int height) {
        heuristic = new double[width][height];
        //init distance map
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // init. every other pixel with infinity distance
                heuristic[x][y] = Double.POSITIVE_INFINITY;
            }
        }
    }

    public void calculateHeuristic(int width, int height, Position start, Position goal, boolean[][] walls) {
        initHeuristic(width, height);
        //todo include error checking if we have calculated the distance to all valid pixels
        Queue<Position> processingStack = new LinkedBlockingQueue<>();
        heuristic[goal.getX()][goal.getY()] = 0;//per definition the distance to the goal is 0
        processingStack.add(goal);
        while (!processingStack.isEmpty()) {
            Position current = processingStack.poll();
            for (Position neighbour : distanceMetric.getNeighbouringPixels(current, 1, width, height)) {
                if (walls[neighbour.getX()][neighbour.getY()])
                    continue;
                double newHeuristic = heuristic[current.getX()][current.getY()] + distanceMetric.getDistance(current, neighbour);
                if (newHeuristic < heuristic[neighbour.getX()][neighbour.getY()]) {
                    //if our distance changed we also need to re-calculate the distance of all our neighbours.
                    heuristic[neighbour.getX()][neighbour.getY()] = newHeuristic;
                    processingStack.add(neighbour);
                }
            }
        }
    }

    public double getHeuristic(Position position) {
        return this.heuristic[position.getX()][position.getY()];
    }
}
