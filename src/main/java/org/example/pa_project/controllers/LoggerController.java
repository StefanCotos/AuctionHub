package org.example.pa_project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/logger")
public class LoggerController {
    private static final String LOG_FILE_PATH = "src/main/java/org/example/pa_project/Logs/application.log";

    @GetMapping
    public ResponseEntity<String> getLogFile() {
        try {
            Path path = Paths.get(LOG_FILE_PATH);
            String content = new String(Files.readAllBytes(path));
            return new ResponseEntity<>(content, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to read log file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
