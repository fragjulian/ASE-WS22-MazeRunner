package com.example.mazerunner;

public interface DistanceMetric {
    /**
     * @param a position of starting point
     * @param b position of ending point
     * @return distance between two points according to the distance metric
     */
    public double getDistance(Position a, Position b);
}
