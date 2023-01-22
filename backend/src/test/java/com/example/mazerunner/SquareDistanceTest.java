package com.example.mazerunner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SquareDistanceTest {
    private DistanceMetric squareDistance;

    @BeforeEach
    public void setup() {
        squareDistance = new SquareDistance();
    }

    @Test
    public void neighbouringPixelsMiddle() {
        List<Position> result = squareDistance.getNeighbouringPixels(new Position(2, 2), 1, 5, 5);
        assertTrue(result.contains(new Position(2, 1)));
        assertTrue(result.contains(new Position(2, 3)));
        assertTrue(result.contains(new Position(1, 2)));
        assertTrue(result.contains(new Position(3, 2)));
        assertEquals(4, result.size());
    }

    @Test
    public void neighbouringPixelsLeftUpperCorner() {
        List<Position> result = squareDistance.getNeighbouringPixels(new Position(0, 0), 1, 5, 5);
        assertTrue(result.contains(new Position(0, 1)));
        assertTrue(result.contains(new Position(1, 0)));
        assertEquals(2, result.size());
    }

    @Test
    public void neighbouringPixelsRightLowerCorner() {
        List<Position> result = squareDistance.getNeighbouringPixels(new Position(4, 4), 1, 5, 5);
        assertTrue(result.contains(new Position(3, 4)));
        assertTrue(result.contains(new Position(4, 3)));
        assertEquals(2, result.size());
    }

    @Test
    public void neighboursOfNeighbours() {
        List<Position> result = squareDistance.getNeighbouringPixels(new Position(2, 2), 2, 5, 5);
        //neighbours of 2 2
        assertTrue(result.contains(new Position(2, 1)));
        assertTrue(result.contains(new Position(2, 3)));
        assertTrue(result.contains(new Position(1, 2)));
        assertTrue(result.contains(new Position(3, 2)));
        //neighbours of neighbours
        assertTrue(result.contains(new Position(0, 2)));
        assertTrue(result.contains(new Position(1, 1)));
        assertTrue(result.contains(new Position(1, 3)));
        assertTrue(result.contains(new Position(2, 0)));
        assertTrue(result.contains(new Position(2, 4)));
        assertTrue(result.contains(new Position(3, 1)));
        assertTrue(result.contains(new Position(3, 3)));
        assertTrue(result.contains(new Position(3, 2)));

        assertEquals(12, result.size());
    }

    @Test
    public void getDistanceSelf() {
        assertEquals(0, squareDistance.getDistance(new Position(0, 1), new Position(0, 1)));
    }

    @Test
    public void getDistance() {
        assertEquals(4, squareDistance.getDistance(new Position(0, 0), new Position(2, 2)));
    }

}
