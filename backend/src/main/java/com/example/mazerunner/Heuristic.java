package com.example.mazerunner;

public interface Heuristic {
    void calculateHeuristic(int width, int height, Position start, Position goal, boolean[][] walls);

    double getHeuristic(Position position);
}
