package com.example.mazerunner;

import java.awt.image.BufferedImage;

public interface Heuristic {
    void calculateHeuristic(BufferedImage bufferedImage, Position start, Position goal, boolean[][] walls);

    double getHeuristic(Position position);
}
