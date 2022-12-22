package com.example.mazerunner;

import java.awt.*;

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

    public WallDetector getWallDetector(String wallDetector, Integer wallColor, Integer obstacleColor, Integer safetyDistance, DistanceMetric distanceMetric) {
        return switch (wallDetector) {
            case "colorwalldetector" -> {
                int wallColorInt = wallColor == null ? DEFAULT_WALL_COLOUR : wallColor;
                int obstacleColorInt = obstacleColor == null ? DEFAULT_OBSTACLE_COLOUR : obstacleColor;
                int safetyDistanceInt = safetyDistance == null ? DEFAULT_SAFETY_DISTANCE : safetyDistance;
                yield new ColorWallDetector(wallColorInt, obstacleColorInt, safetyDistanceInt, new EuclideanDistance());
            }
            default -> new ColorWallDetector(wallColor, obstacleColor, safetyDistance, distanceMetric);
        };
    }

    public DistanceMetric getDistanceMetric(String distanceMetricParameter) {
        return switch (distanceMetricParameter) {
            case "euclidean" -> new EuclideanDistance();
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
