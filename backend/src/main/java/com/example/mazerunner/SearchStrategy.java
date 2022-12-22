package com.example.mazerunner;

import java.awt.image.BufferedImage;

public interface SearchStrategy {
    void calculateShortestPath(BufferedImage bufferedImage, Heuristic heuristic, boolean[][] walls, Position start, Position goal, int pathColor);


}
