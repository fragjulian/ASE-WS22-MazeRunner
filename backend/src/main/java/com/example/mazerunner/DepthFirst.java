package com.example.mazerunner;

import java.util.LinkedList;
import java.util.List;

public class DepthFirst implements SearchStrategy {
    public void calculateShortestPath(Maze maze) {
        Position current = maze.getStart();
        List<Position> alreadyVisited = new LinkedList<>();//todo implement backtracking
        while (!current.equals(maze.getGoal())) {
            maze.paintOnMaze(current, maze.getPathColor());
            Position cheapestNeighbour = null;
            for (int x = current.getX() - 1; x <= current.getX() + 1; x++) {
                for (int y = current.getY() - 1; y <= current.getY() + 1; y++) {
                    Position currentNeighbour = new Position(x, y);
                    //only iterate over neighbours or valid pixels
                    if ((x == current.getX() && y == current.getY()) || x < 0 || x >= maze.getWidth() || y < 0 || y >= maze.getHeight() || maze.getWall(new Position(x, y)) || alreadyVisited.contains(currentNeighbour))
                        continue;
                    if (cheapestNeighbour == null || maze.getHeuristic(cheapestNeighbour) > maze.getHeuristic(currentNeighbour) && !alreadyVisited.contains(cheapestNeighbour))
                        cheapestNeighbour = currentNeighbour;
                }
            }
            //todo backtrack if we did not find a next cheapest neighbour
            alreadyVisited.add(current);
            current = cheapestNeighbour;
        }
    }
}
