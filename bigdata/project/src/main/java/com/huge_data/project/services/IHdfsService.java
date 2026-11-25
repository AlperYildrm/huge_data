package com.huge_data.project.services;

import java.io.IOException;

public interface IHdfsService {

    byte[] readImage(String fileName) throws IOException;
}
