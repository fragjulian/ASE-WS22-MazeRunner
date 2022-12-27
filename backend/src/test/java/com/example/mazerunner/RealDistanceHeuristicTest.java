package com.example.mazerunner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RealDistanceHeuristicTest {
    private final DistanceMetric distanceMetric = new EuclideanDistance();


    private RealDistanceHeuristic realDistanceHeuristicEuclidean;


    @BeforeEach
    public void setup() {
        realDistanceHeuristicEuclidean = new RealDistanceHeuristic(distanceMetric);
    }

    @Test
    public void heuristicNoWallsNeighbour() {
        int size = 2;
        boolean[][] walls = new boolean[size][size];
        Position start = new Position(0, 0);
        Position goal = new Position(size - 1, size - 1);
        realDistanceHeuristicEuclidean.calculateHeuristic(size, size, start, goal, walls);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                assertEquals(distanceMetric.getDistance(new Position(x, y), goal), realDistanceHeuristicEuclidean.getHeuristic(new Position(x, y)));
            }
        }
    }

    @Test
    public void heuristicNoWay() {
        int size = 3;
        boolean[][] walls = new boolean[size][size];
        walls[1][2] = true;
        walls[1][1] = true;
        walls[2][1] = true;
        Position start = new Position(0, 0);
        Position goal = new Position(size - 1, size - 1);
        realDistanceHeuristicEuclidean.calculateHeuristic(size, size, start, goal, walls);
        assertEquals(realDistanceHeuristicEuclidean.getHeuristic(start), Double.POSITIVE_INFINITY);
    }

    @Test
    public void heuristicNoWayReverse() {
        int size = 3;
        boolean[][] walls = new boolean[size][size];
        walls[1][0] = true;
        walls[1][1] = true;
        walls[0][1] = true;
        Position start = new Position(size - 1, size - 1);
        Position goal = new Position(0, 0);
        realDistanceHeuristicEuclidean.calculateHeuristic(size, size, start, goal, walls);
        assertEquals(realDistanceHeuristicEuclidean.getHeuristic(start), Double.POSITIVE_INFINITY);
    }

}
