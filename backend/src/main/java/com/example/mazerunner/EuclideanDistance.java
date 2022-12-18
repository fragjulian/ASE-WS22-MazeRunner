package com.example.mazerunner;

public class EuclideanDistance implements DistanceMetric {
    @Override
    public double getDistance(Position a, Position b) {
        return Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2));
    }
}
