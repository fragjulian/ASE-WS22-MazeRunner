package com.example.mazerunner;

import java.awt.*;
import java.util.Arrays;

public class MazeUtilsFactory {
    private static final int DEFAULT_WALL_COLOUR = new Color(0, 0, 0).getRGB();
    private static final int DEFAULT_OBSTACLE_COLOUR = new Color(219, 219, 219).getRGB();
    private static final int DEFAULT_SAFETY_DISTANCE = 1;
    private static final MazeUtilsFactory mazeUtilsFactory = new MazeUtilsFactory();

    private MazeUtilsFactory() {
    }

    public static MazeUtilsFactory getMazeUtilsFactory() {
        return mazeUtilsFactory;
    }

    public WallDetector getWallDetector(String wallDetector, String wallColorParameter, String obstacleColorParameter, Integer safetyDistance, DistanceMetric distanceMetric) {
        return switch (wallDetector) {
            case "colorwalldetector" -> {
                int wallColorInt = getColor(wallColorParameter, DEFAULT_WALL_COLOUR, "wallColor");
                int obstacleColorInt = getColor(obstacleColorParameter, DEFAULT_OBSTACLE_COLOUR, "obstacleColor");
                int safetyDistanceInt = safetyDistance == null ? DEFAULT_SAFETY_DISTANCE : safetyDistance;
                yield new ColorWallDetector(wallColorInt, obstacleColorInt, safetyDistanceInt, new EuclideanDistance());
            }
            case "edgeDetection" -> new EdgeDetectionWallDetector();
            default ->
                    new ColorWallDetector(DEFAULT_WALL_COLOUR, DEFAULT_OBSTACLE_COLOUR, safetyDistance, distanceMetric);
        };
    }

    private Integer getColor(String colorParameter, int defaultColor, String parameterDescription) {
        if (colorParameter == null)
            return defaultColor;
        String[] colorParts = colorParameter.split(",");
        if (!(colorParts.length == 3 && Arrays.stream(colorParts).allMatch(s -> s.matches("\\d+"))))
            throw new IllegalArgumentException("parameter " + parameterDescription + " must be of the form r,g,b");
        return new Color(Integer.parseInt(colorParts[0]), Integer.parseInt(colorParts[1]), Integer.parseInt(colorParts[2])).getRGB();
    }


    public DistanceMetric getDistanceMetric(String distanceMetricParameter) {
        return switch (distanceMetricParameter) {
            case "euclidean" -> new EuclideanDistance();
            case "square" -> new SquareDistance();
            default -> new EuclideanDistance();
        };
    }

    public Heuristic getHeuristic(String heuristic, DistanceMetric distanceMetric) {
        return switch (heuristic) {
            case "realdistanceheuristic" -> new RealDistanceHeuristic(distanceMetric);
            default -> new RealDistanceHeuristic(distanceMetric);
        };
    }

    public SearchStrategy getSearchStrategy(String searchStrategy) {
        return switch (searchStrategy) {
            case "depthfirst" -> new DepthFirst();
            default -> new DepthFirst();
        };
    }
}
