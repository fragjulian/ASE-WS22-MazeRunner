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
    private final MazeUtilsFactory mazeUtilsFactory = MazeUtilsFactory.getMazeUtilsFactory();
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
                                                 @RequestParam(name = "searchStrategy", required = false, defaultValue = "depthfirst") String searchStrategyParameter,
                                                 @RequestParam(name = "startcolor", required = false) String startColorParameter,//unfortunately cannot use the constant here as default due to spring
                                                 @RequestParam(name = "goalcolor", required = false) String goalColorParameter //unfortunately cannot use the constant here as default due to spring
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
            DistanceMetric distanceMetric = mazeUtilsFactory.getDistanceMetric(distanceMetricParameter);
            WallDetector wallDetector;
            wallDetector = mazeUtilsFactory.getWallDetector(wallDetectorParameter,
                    wallColorParameter, obstacleColorParameter,
                    safetyDistanceParameter, distanceMetric);
            Integer startColor = mazeUtilsFactory.getStartColor(startColorParameter);
            Integer goalColor = mazeUtilsFactory.getGoalColor(goalColorParameter);
            Heuristic heuristic = mazeUtilsFactory.getHeuristic(heuristicParameter, distanceMetric);
            SearchStrategy searchStrategy = mazeUtilsFactory.getSearchStrategy(searchStrategyParameter);
            File temp = File.createTempFile("maze", ".temp");
            file.transferTo(temp);
            BufferedImage bufferedImage = ImageIO.read(temp);
            Maze maze = new Maze(bufferedImage, heuristic, wallDetector, searchStrategy, IMAGE_TYPE, PATH_COLOUR, DEFAULT_BACKGROUND_COLOR, distanceMetric, startColor, goalColor);
            bufferedImage = maze.getSolvedMaze();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            return CompletableFuture.completedFuture(byteArrayOutputStream.toByteArray());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @Async
    @CrossOrigin()
    @PostMapping(value = "/api/maze/path", consumes = "application/json", produces = "application/json")
    public CompletableFuture<ResponseEntity<Path>> uploadMazeData(@RequestBody JsonWallDetector wallDetector,
                                                                  @RequestParam(name = "sizeX") int sizeX,
                                                                  @RequestParam(name = "sizeY") int sizeY,
                                                                  @RequestParam(name = "startX") int startX,
                                                                  @RequestParam(name = "startY") int startY,
                                                                  @RequestParam(name = "goalX") int goalX,
                                                                  @RequestParam(name = "goalY") int goalY,
                                                                  @RequestParam(name = "safetydistance", required = false) Integer safetyDistanceParameter,//unfortunately cannot use the constant here as default due to spring
                                                                  @RequestParam(name = "distancemetric", defaultValue = "euclidean", required = false) String distanceMetricParameter,
                                                                  @RequestParam(name = "heuristic", required = false, defaultValue = "realdistanceheuristic") String heuristicParameter,
                                                                  @RequestParam(name = "searchStrategy", defaultValue = "depthfirst", required = false) String searchStrategyParameter
    ) {
        try {
            if (safetyDistanceParameter != null)
                wallDetector.setSafetyDistance(safetyDistanceParameter);
            wallDetector.setDistanceMetric(new EuclideanDistance());
            DistanceMetric distanceMetric = mazeUtilsFactory.getDistanceMetric(distanceMetricParameter);
            Position start = new Position(startX, startY);
            Position goal = new Position(goalX, goalY);
            Maze maze = new Maze(sizeX, sizeY, mazeUtilsFactory.getHeuristic(heuristicParameter, distanceMetric), wallDetector, mazeUtilsFactory.getSearchStrategy(searchStrategyParameter), distanceMetric, start, goal);
            return CompletableFuture.completedFuture(ResponseEntity.ok(maze.getSolutionPath()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
