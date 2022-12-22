package com.example.mazerunner;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@RestController
public class MazeSolverController {
    private static final int IMAGE_TYPE = BufferedImage.TYPE_INT_RGB;
    private static final int PATH_COLOUR = new Color(255, 0, 0).getRGB();
    private static final int DEFAULT_BACKGROUND_COLOR = Color.WHITE.getRGB();
    private final MazeUtilsFactory mazeUtilsFactory = MazeUtilsFactory.getMazeUtilsFactory();

    /**
     * @param file                    multipart file containing the image
     * @param wallDetectorParameter   which wall detector to use. Possible options: colorwalldetector, default is colorwalldetector
     * @param wallColorParameter      wallColor parameter for the wall detector can be set if wall detector uses such a parameter. The color must be and rgb value in a comma-separated list (example for black: 0,0,0)
     * @param obstacleColorParameter  obstacleColor parameter for the wall detector can be set if wall detector uses such a parameter. The color must be and rgb value in a comma-separated list (example for black: 0,0,0)
     * @param safetyDistanceParameter minimum distance to obstacles measured by distanceMetric
     * @param heuristicParameter      which heuristic to use. Possible options: realdistanceheuristic, default is realdistanceheuristic
     * @param searchStrategyParameter which search strategy to use. Possible options: depthfirst, default is depthfirst
     * @param distanceMetricParameter which distance metric to use. Possible options: euclidean, default is euclidean
     * @return an image containing the solved maze
     * @throws IOException
     */
    @PostMapping(value = "/api/maze", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] uploadImage(@RequestParam("image") MultipartFile file, @RequestParam(name = "walldetector", required = false, defaultValue = "colorwalldetector") String wallDetectorParameter, @RequestParam(name = "wallcolor", required = false) String wallColorParameter,//unfortunately cannot use the constant here as default due to spring
                              @RequestParam(name = "obstaclecolor", required = false) String obstacleColorParameter,//unfortunately cannot use the constant here as default due to spring
                              @RequestParam(name = "safetydistance", required = false) Integer safetyDistanceParameter,//unfortunately cannot use the constant here as default due to spring
                              @RequestParam(name = "heuristic", required = false, defaultValue = "realdistanceheuristic") String heuristicParameter, @RequestParam(name = "searchstrategy", required = false, defaultValue = "depthfirst") String searchStrategyParameter, @RequestParam(name = "distancemetric", required = false, defaultValue = "euclidean") String distanceMetricParameter) throws IOException {

        DistanceMetric distanceMetric = mazeUtilsFactory.getDistanceMetric(distanceMetricParameter);
        WallDetector wallDetector;
        try {
            wallDetector = mazeUtilsFactory.getWallDetector(wallDetectorParameter,
                    wallColorParameter, obstacleColorParameter,
                    safetyDistanceParameter, distanceMetric);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        Heuristic heuristic = mazeUtilsFactory.getHeuristic(heuristicParameter, distanceMetric);
        SearchStrategy searchStrategy = mazeUtilsFactory.getSearchStrategy(searchStrategyParameter);
        File temp = File.createTempFile("maze", ".temp");
        file.transferTo(temp);
        BufferedImage bufferedImage = ImageIO.read(temp);
        Maze maze = new Maze(bufferedImage, heuristic, wallDetector, searchStrategy, IMAGE_TYPE, PATH_COLOUR, DEFAULT_BACKGROUND_COLOR);
        bufferedImage = maze.solveMaze();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();

    }

}
