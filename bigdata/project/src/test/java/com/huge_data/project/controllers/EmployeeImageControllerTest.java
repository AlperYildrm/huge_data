package com.huge_data.project.controllers;

import com.huge_data.project.services.IHdfsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileNotFoundException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeImageController.class)
class EmployeeImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IHdfsService hdfsService;

    @Test
    void imageFromHdfsIsReturnedAsJpeg() throws Exception {
        byte[] image = {1, 2, 3};
        when(hdfsService.readImage("scott.jpg")).thenReturn(image);

        mockMvc.perform(get("/images/scott.jpg"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(image));
    }

    @Test
    void missingImageReturnsNotFound() throws Exception {
        when(hdfsService.readImage("nobody.jpg")).thenThrow(new FileNotFoundException("nobody.jpg"));

        mockMvc.perform(get("/images/nobody.jpg"))
                .andExpect(status().isNotFound());
    }
}
