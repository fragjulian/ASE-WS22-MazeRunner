package com.example.mazerunner;

import java.awt.image.BufferedImage;

public class ColourWallDetector implements WallDetector {
    private int wall;

    public ColourWallDetector(int wall) {
        this.wall = wall;
    }

    @Override
    public boolean[][] detectWall(BufferedImage bufferedImage) {
        boolean[][] result = new boolean[bufferedImage.getWidth()][bufferedImage.getHeight()];
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                if (bufferedImage.getRGB(x, y) == wall) {
                    result[x][y] = true;
                }

            }
        }
        return result;
    }
}
