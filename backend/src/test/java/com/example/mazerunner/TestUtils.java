package com.example.mazerunner;

import java.awt.image.BufferedImage;

public class TestUtils {
    public static void initBufferedImageNoWalls(int size, BufferedImage bufferedImage) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                bufferedImage.setRGB(x, y, -1);
            }
        }
    }
}
