package com.example.mazerunner;

import java.awt.image.BufferedImage;

public class ColourWallDetector implements WallDetector {
    private final int WALL_COLOUR;
    private final int OBSTACLE_COLOUR;
    private final int SAFETY_DISTANCE;

    public ColourWallDetector(int WALL_COLOUR, int OBSTACLE_COLOUR, int safetyDistance) {
        this.WALL_COLOUR = WALL_COLOUR;
        this.OBSTACLE_COLOUR = OBSTACLE_COLOUR;
        SAFETY_DISTANCE = safetyDistance;
    }

    @Override
    public boolean[][] detectWall(BufferedImage bufferedImage) {
        boolean[][] result = new boolean[bufferedImage.getWidth()][bufferedImage.getHeight()];
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                result[x][y] = result[x][y] || bufferedImage.getRGB(x, y) == WALL_COLOUR;//if the pixel has the wall colour, mark it as wall. If this pixel was already marked by an obstacle ignore it
                if (bufferedImage.getRGB(x, y) == OBSTACLE_COLOUR) {//see if some obstacle is near
                    for (int xNeighbour = x - SAFETY_DISTANCE; xNeighbour <= x + SAFETY_DISTANCE; xNeighbour++) {
                        for (int yNeighbour = y - SAFETY_DISTANCE; yNeighbour <= y + SAFETY_DISTANCE; yNeighbour++) {
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
