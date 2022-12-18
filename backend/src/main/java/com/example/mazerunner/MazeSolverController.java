package com.example.mazerunner;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@RestController
public class MazeSolverController {
    private static final int IMAGE_TYPE = BufferedImage.TYPE_INT_RGB;
    private static final int DEFAULT_WALL_COLOUR = new Color(0, 0, 0).getRGB();
    private static final int DEFAULT_OBSTACLE_COLOUR = new Color(219, 219, 219).getRGB();
    private static final int DEFAULT_SAFETY_DISTANCE = 1;
    private static final int PATH_COLOUR = new Color(255, 0, 0).getRGB();
    private static final WallDetector DEFAULT_WALL_DETECTOR = new ColourWallDetector(DEFAULT_WALL_COLOUR, DEFAULT_OBSTACLE_COLOUR, DEFAULT_SAFETY_DISTANCE);
    private final DistanceMetric DEFAULT_DISTANCE_METRIC = new EuclideanDistance();
    private final SearchStrategy DEFAULT_SEARCH_STRATEGY = new DepthFirst();
    private final Heuristic DEFAULT_HEURISTIC = new RealDistanceHeuristic(DEFAULT_DISTANCE_METRIC);

    @PostMapping(
            value = "/api/maze",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] uploadImage(@RequestParam("image") MultipartFile file,
                              @RequestParam(name = "walldetector", required = false, defaultValue = "colourwalldetector") String wallDetectorParameter,
                              @RequestParam(name = "wallcolour", required = false) Integer wallColourParameter,//unfortunately cannot use the constant here as default due to spring
                              @RequestParam(name = "obstaclecolour", required = false) Integer obstacleColourParameter,//unfortunately cannot use the constant here as default due to spring
                              @RequestParam(name = "safetydistance", required = false) Integer safetyDistanceParameter,//unfortunately cannot use the constant here as default due to spring
                              @RequestParam(name = "heuristic", required = false, defaultValue = "realdistanceheuristic") String heuristicParameter,
                              @RequestParam(name = "searchstrategy", required = false, defaultValue = "depthfirst") String searchStrategyParameter,
                              @RequestParam(name = "distancemetric", required = false, defaultValue = "euclidean") String distanceMetricParameter) throws IOException {

        WallDetector wallDetector = switch (wallDetectorParameter) {
            case "colourwalldetector" -> {
                int wallColour = wallColourParameter == null ? DEFAULT_WALL_COLOUR : wallColourParameter;
                int obstacleColour = obstacleColourParameter == null ? DEFAULT_OBSTACLE_COLOUR : obstacleColourParameter;
                int safetyDistance = safetyDistanceParameter == null ? DEFAULT_SAFETY_DISTANCE : safetyDistanceParameter;
                yield new ColourWallDetector(wallColour, obstacleColour, safetyDistance);
            }
            default -> DEFAULT_WALL_DETECTOR;
        };

        DistanceMetric distanceMetric = switch (distanceMetricParameter) {
            case "euclidean" -> new EuclideanDistance();
            default -> DEFAULT_DISTANCE_METRIC;
        };

        Heuristic heuristic = switch (heuristicParameter) {
            case "realdistanceheuristic" -> new RealDistanceHeuristic(distanceMetric);
            default -> DEFAULT_HEURISTIC;
        };
        //solve maze and return the solved image
        File temp = File.createTempFile("maze", ".temp");
        file.transferTo(temp);
        BufferedImage bufferedImage = ImageIO.read(temp);
        Maze maze = new Maze(bufferedImage, heuristic, wallDetector, switch (searchStrategyParameter) {
            case "depthfirst" -> new DepthFirst();
            default -> DEFAULT_SEARCH_STRATEGY;
        }, IMAGE_TYPE, PATH_COLOUR);
        bufferedImage = maze.solveMaze();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}
