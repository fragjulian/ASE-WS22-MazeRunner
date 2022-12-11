package com.example.mazerunner;

public class EuclideanDistance implements DistanceMetric {
    @Override
    public double getDistance(Position a, Position b) {
        return Math.sqrt((a.x - b.x) ^ 2 + (b.x - b.y) ^ 2);
    }
}
