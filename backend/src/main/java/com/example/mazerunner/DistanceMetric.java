package com.example.mazerunner;

import java.util.List;

public interface DistanceMetric {
    /**
     * @param a position of starting point
     * @param b position of ending point
     * @return distance between two points according to the distance metric
     */
    double getDistance(Position a, Position b);

    /**
     * @param position the pixel to which to get the neighbouring pixels
     * @param width    maximum width of the image. No pixels outside this range will be returned
     * @param height   maximum height of the image. No pixels outside this range will be returned
     * @return a List containing all the pixels next to @param position. No pixels outside the image or smaller than zero shall be returned
     */
    List<Position> getNeighbouringPixels(Position position, int radius, int width, int height);

}
