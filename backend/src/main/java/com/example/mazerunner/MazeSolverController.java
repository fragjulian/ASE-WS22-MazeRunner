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

    private static final int WALL_COLOUR = new Color(0, 0, 0).getRGB();

    private static final int PATH_COLOUR = new Color(255, 0, 0).getRGB();
    private static final WallDetector DEFAULT_WALL_DETECTOR = new ColourWallDetector(WALL_COLOUR);
    private final DistanceMetric DEFAULT_DISTANCE_METRIC = new EuclideanDistance();
    private final Heuristic DEFAULT_HEURISTIC = new RealDistanceHeuristic(DEFAULT_DISTANCE_METRIC);

    @PostMapping(
            value = "/api/maze",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] uploadImage(@RequestParam("image") MultipartFile file,
                              @RequestParam(name = "walldetector", required = false) String wallDetectorParameter,
                              @RequestParam(name = "wallcolour", required = false) Integer colour,//unfortunately cannot use the constant here due to spring
                              @RequestParam(name = "heuristic", required = false, defaultValue = "realdistanceheuristic") String heuristicParameter,
                              @RequestParam(name = "distancemetric", required = false, defaultValue = "euclidean") String distanceMetricParameter) throws IOException {
        WallDetector wallDetector = DEFAULT_WALL_DETECTOR;
        if (wallDetectorParameter != null)
            if (wallDetectorParameter.equals("colourwalldetector")) {
                if (colour == null)
                    wallDetector = new ColourWallDetector(WALL_COLOUR);
                else
                    wallDetector = new ColourWallDetector(colour);
            } else {
                wallDetector = DEFAULT_WALL_DETECTOR;
            }
        DistanceMetric distanceMetric;
        if (distanceMetricParameter.equals("euclidean")) {
            distanceMetric = new EuclideanDistance();
        } else {
            distanceMetric = DEFAULT_DISTANCE_METRIC;
        }

        Heuristic heuristic;
        if (heuristicParameter.equals("realdistanceheuristic")) {
            heuristic = new RealDistanceHeuristic(distanceMetric);
        } else {
            heuristic = DEFAULT_HEURISTIC;
        }
        //solve maze and return the solved image
        File temp = File.createTempFile("maze", ".temp");
        file.transferTo(temp);
        BufferedImage bufferedImage = ImageIO.read(temp);
        Maze maze = new Maze(bufferedImage, heuristic, wallDetector, IMAGE_TYPE, PATH_COLOUR);
        bufferedImage = maze.solveMaze();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}
