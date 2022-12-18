package com.example.mazerunner;

import java.awt.image.BufferedImage;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class RealDistanceHeuristic implements Heuristic {
    private final DistanceMetric distanceMetric;
    private double[][] heuristic;

    public RealDistanceHeuristic(DistanceMetric distanceMetric) {
        this.distanceMetric = distanceMetric;
    }

    private void initHeuristic(BufferedImage bufferedImage) {
        heuristic = new double[bufferedImage.getWidth()][bufferedImage.getHeight()];
        //init distance map
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                // init. every other pixel with infinity distance
                heuristic[x][y] = Double.POSITIVE_INFINITY;
            }
        }
    }

    public void calculateHeuristic(BufferedImage bufferedImage, Position start, Position goal, boolean[][] walls) {
        initHeuristic(bufferedImage);
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

    public double getHeuristic(Position position) {
        return this.heuristic[position.x][position.y];
    }
}
