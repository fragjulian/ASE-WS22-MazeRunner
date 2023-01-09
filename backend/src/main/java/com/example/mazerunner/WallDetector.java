package com.example.mazerunner;

public interface WallDetector {
    /**
     * detect walls and obstacles in a bufferedImage
     *
     * @return a 2D boolean array. False means that no wall or obstacle with including safety distance was detected. True means that one was detected
     */
    boolean[][] detectWall(Maze maze);
}
