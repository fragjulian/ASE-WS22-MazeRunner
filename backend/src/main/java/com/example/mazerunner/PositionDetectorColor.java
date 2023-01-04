package com.example.mazerunner;

import java.awt.image.BufferedImage;

public class PositionDetectorColor {

    public static Position getPositionByColor(BufferedImage image, int color) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (image.getRGB(x, y) == color) {
                    System.out.println(image.getRGB(x, y) + "=" + color + " x,y = " + x + ", " + y);
                    return new Position(x, y);
                }
            }
        }

        return new Position(0, 0); //default position if not found
    }
}
