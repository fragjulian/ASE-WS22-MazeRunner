package com.example.mazerunner;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class MazeSolverController {
    @PostMapping("/solve-maze")
    public String uploadImage(@RequestParam("image") MultipartFile file)
            throws IOException {
        //solve maze and return the solved image
        return "image uploaded";
    }

}
