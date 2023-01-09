package com.example.mazerunner;

import org.apache.tika.Tika;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
public class MazeSolverController {
    private static final int IMAGE_TYPE = BufferedImage.TYPE_INT_RGB;
    private static final int PATH_COLOUR = new Color(255, 0, 0).getRGB();
    private static final int DEFAULT_BACKGROUND_COLOR = Color.WHITE.getRGB();
    private final long MAX_FILE_SIZE = 100000;//max file size allowed for upload

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

    /*Found a solution for this fail message:

    Access to XMLHttpRequest at 'http://localhost:8080/api/maze'
    from origin >'http://localhost:4200/' has been blocked by CORS policy:
    No 'Access-Control->Allow-Origin' header is present on the requested resource.

    here: https://spring.io/guides/gs/rest-service-cors/

    Now it allows cross-origin resource sharing.

    */
    @CrossOrigin()
    @Async
    @PostMapping(value = "/api/maze/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public CompletableFuture<byte[]> uploadImage(@RequestParam("image") MultipartFile file, @RequestParam(name = "wallcolor", required = false) String wallColorParameter,//unfortunately cannot use the constant here as default due to spring
                                                 @RequestParam(name = "obstaclecolor", required = false) String obstacleColorParameter,//unfortunately cannot use the constant here as default due to spring
                                                 @RequestParam(name = "safetydistance", required = false, defaultValue = "1") Integer safetyDistanceParameter,//unfortunately cannot use the constant here as default due to spring
                                                 @RequestParam(name = "distancemetric", required = false, defaultValue = "euclidean") String distanceMetricParameter,
                                                 @RequestParam(name = "wallDetector", required = false, defaultValue = "colorwalldetector") String wallDetectorParameter,
                                                 @RequestParam(name = "heuristic", required = false, defaultValue = "realdistanceheuristic") String heuristicParameter,
                                                 @RequestParam(name = "searchStrategy", required = false, defaultValue = "depthfirst") String searchStrategyParameter
    ) throws IOException {
        if (file == null || file.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "image parameter must contain a valid maze");
        if (file.getSize() > MAX_FILE_SIZE)
            throw new ResponseStatusException(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED, "file size must not exceed " + MAX_FILE_SIZE);
        Tika tika = new Tika();
        if (!(tika.detect(file.getBytes()).equals(file.getContentType()) && file.getContentType().equals("image/png") || file.getContentType().equals("image/jpg")))
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "unsupported file type");
        try {
            File temp = File.createTempFile("maze", ".temp");
            file.transferTo(temp);
            BufferedImage bufferedImage = ImageIO.read(temp);
            Maze maze = new MazeParameterBuilder()
                    .setDistanceMetric(distanceMetricParameter)
                    .setHeuristic(heuristicParameter)
                    .setWallDetector(wallDetectorParameter, wallColorParameter, obstacleColorParameter, safetyDistanceParameter)
                    .setImageType(IMAGE_TYPE)
                    .setPathColor(PATH_COLOUR)
                    .setBackgroundColor(DEFAULT_BACKGROUND_COLOR)
                    .setSearchStrategy(searchStrategyParameter).build(bufferedImage);
            bufferedImage = maze.getSolvedMaze();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            return CompletableFuture.completedFuture(byteArrayOutputStream.toByteArray());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
     * @param sizeX                   the width of the maze in pixels
     * @param sizeY                   the height of the maze in pixels
     * @param safetyDistanceParameter minimum distance to obstacles measured by distanceMetric
     * @param heuristicParameter      which heuristic to use. Possible options: realdistanceheuristic, default is realdistanceheuristic
     * @param searchStrategyParameter which search strategy to use. Possible options: depthfirst, default is depthfirst
     * @param distanceMetricParameter which distance metric to use. Possible options: euclidean, default is euclidean
     * @param wallDetector            walls and obstacles in json form from the maze builder. Example: {"walls":[{"x":1,"y":1}],"obstacles":[{"x":2,"y":3}] }
     * @return an image containing the solved maze
     */
    @Async
    @CrossOrigin()
    @PostMapping(value = "/api/maze/path", consumes = "application/json", produces = "application/json")
    public CompletableFuture<ResponseEntity<Path>> uploadMazeData(@RequestBody JsonWallDetector wallDetector,
                                                                  @RequestParam(name = "sizeX") int sizeX,
                                                                  @RequestParam(name = "sizeY") int sizeY,
                                                                  @RequestParam(name = "safetydistance", defaultValue = "2", required = false) Integer safetyDistanceParameter,//unfortunately cannot use the constant here as default due to spring
                                                                  @RequestParam(name = "distancemetric", defaultValue = "euclidean", required = false) String distanceMetricParameter,
                                                                  @RequestParam(name = "heuristic", required = false, defaultValue = "realdistanceheuristic") String heuristicParameter,
                                                                  @RequestParam(name = "searchStrategy", defaultValue = "depthfirst", required = false) String searchStrategyParameter
    ) {
        try {
            Maze maze = new MazeParameterBuilder().setDistanceMetric(distanceMetricParameter).setSafetyDistance(safetyDistanceParameter).setHeuristic(heuristicParameter).setSearchStrategy(searchStrategyParameter).setWallDetector(wallDetector).build(sizeX,sizeY);
            return CompletableFuture.completedFuture(ResponseEntity.ok(maze.getSolutionPath()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
