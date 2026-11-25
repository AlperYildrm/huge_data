package com.huge_data.project.config;

import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URI;

@Configuration
public class HdfsConfig {

    @Value("${hdfs.uri}")
    private String hdfsUri;

    @Bean
    public FileSystem hdfsFileSystem() throws IOException {
        org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();
        configuration.set("fs.defaultFS", hdfsUri);
        return FileSystem.get(URI.create(hdfsUri), configuration);
    }
}
