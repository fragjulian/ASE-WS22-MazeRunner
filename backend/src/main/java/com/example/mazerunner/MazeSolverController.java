package com.example.mazerunner;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MazeSolverController {
    @PostMapping("/api/maze")
    public String uploadImage(@RequestParam("image") MultipartFile file) {
        //solve maze and return the solved image
        return "image uploaded";
    }

}
