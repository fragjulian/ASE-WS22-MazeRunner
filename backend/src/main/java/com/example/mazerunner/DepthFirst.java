package com.example.mazerunner;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.List;

public class DepthFirst implements SearchStrategy {
    public Path calculateShortestPath(Maze maze) {
        Path path = new Path();
        Position current = maze.getStart();
        List<Position> alreadyVisited = new LinkedList<>();//todo implement backtracking
        while (current != null && !current.equals(maze.getGoal())) {
            //maze.paintOnMaze(current, maze.getPathColor());
            path.addStep(current);
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
        if (current == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "maze is unsolvable");
        path.removeStep(maze.getStart());
        return path;
    }
}
