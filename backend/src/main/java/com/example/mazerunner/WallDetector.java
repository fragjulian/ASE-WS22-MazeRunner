package com.example.mazerunner;

import java.awt.image.BufferedImage;

public interface WallDetector {
    public boolean[][] detectWall(BufferedImage bufferedImage);
}
