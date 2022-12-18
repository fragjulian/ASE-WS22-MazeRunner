package com.example.mazerunner;

public class EuclideanDistance implements DistanceMetric {
    @Override
    public double getDistance(Position a, Position b) {
        return Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
    }
}
