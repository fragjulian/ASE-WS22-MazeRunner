package com.example.mazerunner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class MazeRunnerApplicationTests {
    private MazeSolverController mazeSolverController;

    @BeforeEach
    public void setup() {
        mazeSolverController = new MazeSolverController();
    }

    @Test
    void pathApiTest() throws ExecutionException, InterruptedException {
        JsonWallDetector jsonWallDetector = new JsonWallDetector();
        Position[] walls = {new Position(1, 0), new Position(1, 1)};
        jsonWallDetector.setWalls(walls);
        jsonWallDetector.setObstacles(new Position[0]);
        ResponseEntity<Path> responseEntity = mazeSolverController.uploadMazeData(jsonWallDetector, 3, 3, 1, 0, 0, 2, 2, "euclidean", "realdistanceheuristic", "depthfirst").get();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(new Position(0, 1), responseEntity.getBody().getPath().get(0));
        assertEquals(new Position(1, 2), responseEntity.getBody().getPath().get(1));
        assertEquals(2, responseEntity.getBody().getPath().size());
    }

    @Test
    void pathApiInvalidStartPosition() {
        JsonWallDetector jsonWallDetector = new JsonWallDetector();
        jsonWallDetector.setWalls(new Position[0]);
        jsonWallDetector.setObstacles(new Position[0]);
        assertEquals("400 BAD_REQUEST \"maze is unsolvable\"", assertThrows(ResponseStatusException.class, () -> mazeSolverController.uploadMazeData(jsonWallDetector, 3, 3, 1, -2, 0, 2, 2, "euclidean", "realdistanceheuristic", "depthfirst")).getMessage());
    }

    @Test
    void pathApiUnsolvableMaze() {
        JsonWallDetector jsonWallDetector = new JsonWallDetector();
        Position[] walls = {new Position(1, 0), new Position(1, 1), new Position(1, 2)};
        jsonWallDetector.setWalls(walls);
        jsonWallDetector.setObstacles(new Position[0]);
        assertEquals("400 BAD_REQUEST \"maze is unsolvable\"", assertThrows(ResponseStatusException.class, () -> mazeSolverController.uploadMazeData(jsonWallDetector, 3, 3, 1, -2, 0, 2, 2, "euclidean", "realdistanceheuristic", "depthfirst")).getMessage());
    }


}
