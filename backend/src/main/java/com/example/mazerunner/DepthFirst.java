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
            for (Position currentNeighbour : maze.getDistanceMetric().getNeighbouringPixels(current, 1, maze.getWidth(), maze.getHeight())) {
                if (maze.getWall(currentNeighbour) || alreadyVisited.contains(currentNeighbour))
                    continue;
                if (cheapestNeighbour == null || maze.getHeuristic(cheapestNeighbour) > maze.getHeuristic(currentNeighbour) && !alreadyVisited.contains(cheapestNeighbour))
                    cheapestNeighbour = currentNeighbour;
            }
            //todo backtrack if we did not find a next cheapest neighbour
            alreadyVisited.add(current);
            current = cheapestNeighbour;
        }
    }
}
