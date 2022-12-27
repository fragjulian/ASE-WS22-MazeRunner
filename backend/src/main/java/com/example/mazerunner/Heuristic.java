package com.example.mazerunner;

public interface Heuristic {
    /**
     * initialise the heuristic calculation
     *
     * @param width  the width of the image
     * @param height the height of the image
     * @param start  the start position
     * @param goal   the end position
     * @param walls  2d boolean array, true if there is a wall in that pixel, false otherwise
     */
    void calculateHeuristic(int width, int height, Position start, Position goal, boolean[][] walls);

    /**
     * get the previously calculated heuristic for a given position
     *
     * @param position the position at which to return the heuristic
     * @return the heuristic value for a spesific position as double
     */
    double getHeuristic(Position position);
}
