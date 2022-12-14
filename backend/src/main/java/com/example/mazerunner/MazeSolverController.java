package com.example.mazerunner;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@RestController
public class MazeSolverController {
    @PostMapping(
            value = "/api/maze",
            produces = MediaType.IMAGE_JPEG_VALUE
    )

    /*Found a solution for this fail message:

    Access to XMLHttpRequest at 'http://localhost:8080/api/maze'
    from origin >'http://localhost:4200' has been blocked by CORS policy:
    No 'Access-Control->Allow-Origin' header is present on the requested resource.

    here: https://spring.io/guides/gs/rest-service-cors/

    Now it allows cross-origin resource sharing.
    */
    @CrossOrigin()

    public byte[] uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        //solve maze and return the solved image
        File temp = File.createTempFile("maze", ".temp");
        file.transferTo(temp);

        BufferedImage bufferedImage2 = ImageIO.read(temp);
        Maze maze = new Maze(bufferedImage2);
        maze.solveMaze();


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage2, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }



}
