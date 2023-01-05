package com.example.mazerunner;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Path {

    private LinkedList<Position> path = new LinkedList();

    public BufferedImage drawPath(BufferedImage bufferedImage, int pathColor) {
        for (Position position : path)
            bufferedImage.setRGB(position.getX(), position.getY(), pathColor);
        return bufferedImage;
    }

    public void addStep(Position position) {
        this.path.add(position);
        //todo check if this is allowed
    }

    public LinkedList<Position> getPath() {
        return path;
    }
}
