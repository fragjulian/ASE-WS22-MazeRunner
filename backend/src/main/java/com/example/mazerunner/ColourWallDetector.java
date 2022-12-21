package com.example.mazerunner;

import java.awt.image.BufferedImage;

public class ColourWallDetector implements WallDetector {
    private final int wallColour;
    private final int obstacleColour;
    private final int safetyDistance;
    private final DistanceMetric distanceMetric;

    public ColourWallDetector(int wallColour, int obstacleColour, int safetyDistance, DistanceMetric distanceMetric) {
        this.wallColour = wallColour;
        this.obstacleColour = obstacleColour;
        this.safetyDistance = safetyDistance;
        this.distanceMetric = distanceMetric;
    }

    @Override
    public boolean[][] detectWall(BufferedImage bufferedImage) {
        boolean[][] result = new boolean[bufferedImage.getWidth()][bufferedImage.getHeight()];
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                result[x][y] = result[x][y] || bufferedImage.getRGB(x, y) == wallColour;//if the pixel has the wall colour, mark it as wall. If this pixel was already marked by an obstacle ignore it
                if (bufferedImage.getRGB(x, y) == obstacleColour) {//see if some obstacle is near
                    for (Position neighbour : distanceMetric.getNeighbouringPixels(new Position(x, y), safetyDistance, bufferedImage.getWidth(), bufferedImage.getHeight()))
                        result[neighbour.getX()][neighbour.getY()] = true;
                }
            }
        }
        return result;
    }
}
