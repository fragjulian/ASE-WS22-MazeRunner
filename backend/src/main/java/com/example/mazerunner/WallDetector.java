package com.example.mazerunner;

import java.awt.image.BufferedImage;

public interface WallDetector {
    /**
     * detect walls and obstacles in a bufferedImage
     *
     * @param bufferedImage
     * @return a 2D boolean array. False means that no wall or obstacle with including safety distance was detected. True means that one was detected
     */
    boolean[][] detectWall(BufferedImage bufferedImage);
}
