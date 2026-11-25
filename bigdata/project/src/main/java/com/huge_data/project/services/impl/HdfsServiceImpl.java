package com.huge_data.project.services.impl;

import com.huge_data.project.services.IHdfsService;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class HdfsServiceImpl implements IHdfsService {

    private final FileSystem hdfsFileSystem;
    private final String basePath;

    public HdfsServiceImpl(FileSystem hdfsFileSystem, @Value("${hdfs.base-path}") String basePath) {
        this.hdfsFileSystem = hdfsFileSystem;
        this.basePath = basePath;
    }

    @Override
    public byte[] readImage(String fileName) throws IOException {
        Path path = new Path(basePath, fileName);

        if (!hdfsFileSystem.exists(path)) {
            throw new FileNotFoundException("File not found in HDFS: " + path);
        }

        try (FSDataInputStream inputStream = hdfsFileSystem.open(path)) {
            return inputStream.readAllBytes();
        }
    }
}
