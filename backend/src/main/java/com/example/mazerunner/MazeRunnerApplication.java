package com.example.mazerunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class MazeRunnerApplication {

    public static void main(String[] args) {
        //nu.pattern.OpenCV.loadLocally();
        //System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
        SpringApplication.run(MazeRunnerApplication.class, args);
    }

}
