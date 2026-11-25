package com.huge_data.project.controllers;

import com.huge_data.project.services.IHdfsService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
public class EmployeeImageController {

    private final IHdfsService hdfsService;

    public EmployeeImageController(IHdfsService hdfsService) {
        this.hdfsService = hdfsService;
    }

    @GetMapping("/images/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) throws IOException {
        try {
            byte[] image = hdfsService.readImage(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
        } catch (FileNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
