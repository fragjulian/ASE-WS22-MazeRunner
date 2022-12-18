package com.example.mazerunner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ColourWallDetectorTest {
    private static final int WALL = -16777216;
    private static final int DEFAULT_OBSTACLE_COLOUR = new Color(219, 219, 219).getRGB();
    private static final int DEFAULT_SAFETY_DISTANCE = 1;
    private static final int SIZE_SMALL = 2;
    private static final int SIZE_MEDIUM = 20;
    private static final int SIZE_LARGE = 200;
    private static final int SIZE_EXTRA_LARGE = 2000;
    private static final int IMG_TYPE = TYPE_INT_ARGB;
    private BufferedImage bufferedImage;

    private WallDetector wallDetector;


    @BeforeEach
    public void setup() {
        wallDetector = new ColourWallDetector(WALL, DEFAULT_OBSTACLE_COLOUR, DEFAULT_SAFETY_DISTANCE);
    }

    @ParameterizedTest
    @ValueSource(ints = {SIZE_SMALL, SIZE_MEDIUM, SIZE_LARGE, SIZE_EXTRA_LARGE})
    public void detectNoWalls(int size) {
        bufferedImage = new BufferedImage(size, size, IMG_TYPE);
        TestUtils.initBufferedImageNoWalls(size, bufferedImage);
        boolean[][] detectedWalls = wallDetector.detectWall(bufferedImage);
        boolean[][] expectedWalls = new boolean[size][size];
        assertArrayEquals(expectedWalls, detectedWalls);
    }

    @ParameterizedTest
    @ValueSource(ints = {SIZE_SMALL, SIZE_MEDIUM, SIZE_LARGE, SIZE_EXTRA_LARGE})
    public void detectWalls(int size) {
        bufferedImage = new BufferedImage(size, size, IMG_TYPE);
        TestUtils.initBufferedImageNoWalls(size, bufferedImage);

        boolean[][] realWalls = new boolean[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {

                if (x == y ||//diagonal wall from top left to bottom right
                        x == size / 2 ||//horizontal wall in the middle and vertical
                        x == 0 ||//horizontal wall top
                        x == size - 1 ||//horizontal wall bottom
                        y == size / 2 || //vertical wall in the middle and vertical
                        y == 0 ||//vertical wall top
                        y == size - 1 //vertical wall bottom
                ) {
                    realWalls[x][y] = true;
                    bufferedImage.setRGB(x, y, WALL);
                }

            }
        }
        boolean[][] detectedWalls = wallDetector.detectWall(bufferedImage);
        assertArrayEquals(realWalls, detectedWalls);
    }
}
