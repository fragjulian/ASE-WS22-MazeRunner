package com.example.mazerunner;

import java.awt.image.BufferedImage;

public class PositionDetectorColor {

    public static Position getPositionByColor(BufferedImage image, int color) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (image.getRGB(x, y) == color) {
                    return new Position(x, y);
                }
            }
        }
        throw new IllegalArgumentException("No Start or Goal Position found with color " + color);
    }
}
