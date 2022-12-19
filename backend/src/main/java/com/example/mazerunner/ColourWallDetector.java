package com.example.mazerunner;

import java.awt.image.BufferedImage;

public class ColourWallDetector implements WallDetector {
    private final int wallColour;
    private final int obstacleColour;
    private final int safetyDistance;

    public ColourWallDetector(int wallColour, int obstacleColour, int safetyDistance) {
        this.wallColour = wallColour;
        this.obstacleColour = obstacleColour;
        this.safetyDistance = safetyDistance;
    }

    @Override
    public boolean[][] detectWall(BufferedImage bufferedImage) {
        boolean[][] result = new boolean[bufferedImage.getWidth()][bufferedImage.getHeight()];
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                result[x][y] = result[x][y] || bufferedImage.getRGB(x, y) == wallColour;//if the pixel has the wall colour, mark it as wall. If this pixel was already marked by an obstacle ignore it
                if (bufferedImage.getRGB(x, y) == obstacleColour) {//see if some obstacle is near
                    for (int xNeighbour = x - safetyDistance; xNeighbour <= x + safetyDistance; xNeighbour++) {
                        for (int yNeighbour = y - safetyDistance; yNeighbour <= y + safetyDistance; yNeighbour++) {
                            if (xNeighbour < 0 || xNeighbour >= bufferedImage.getWidth() || yNeighbour < 0 || yNeighbour >= bufferedImage.getHeight())
                                continue;
                            result[xNeighbour][yNeighbour] = true;
                        }
                    }
                }
            }
        }
        return result;
    }
}
