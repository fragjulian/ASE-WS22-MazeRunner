package com.example.mazerunner;

import java.awt.image.BufferedImage;

public class ColorWallDetector implements WallDetector {
    private final int wallColor;
    private final int obstacleColor;
    private final int safetyDistance;
    private final DistanceMetric distanceMetric;

    public ColorWallDetector(int wallColor, int obstacleColor, int safetyDistance, DistanceMetric distanceMetric) {
        this.wallColor = wallColor;
        this.obstacleColor = obstacleColor;
        this.safetyDistance = safetyDistance;
        this.distanceMetric = distanceMetric;
    }

    @Override
    public boolean[][] detectWall(Maze maze) {
        BufferedImage bufferedImage = maze.getBufferedImage();
        boolean[][] result = new boolean[bufferedImage.getWidth()][bufferedImage.getHeight()];
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                result[x][y] = result[x][y] || bufferedImage.getRGB(x, y) == wallColor;//if the pixel has the wall color, mark it as wall. If this pixel was already marked by an obstacle ignore it
                if (bufferedImage.getRGB(x, y) == obstacleColor) {//see if some obstacle is near
                    for (Position neighbour : distanceMetric.getNeighbouringPixels(new Position(x, y), safetyDistance, bufferedImage.getWidth(), bufferedImage.getHeight()))
                        result[neighbour.getX()][neighbour.getY()] = true;
                }
            }
        }
        return result;
    }
}
