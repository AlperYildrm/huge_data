package com.huge_data.project.controllers;

import com.huge_data.project.dto.DtoEmployeeReport;
import com.huge_data.project.services.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IEmployeeService employeeService;

    @Test
    void indexPageRendersSearchFormWithoutCss() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<form action=\"/\" method=\"get\">")))
                .andExpect(content().string(containsString("name=\"ename\"")))
                .andExpect(content().string(not(containsString("<style"))))
                .andExpect(content().string(not(containsString("style="))))
                .andExpect(content().string(not(containsString("stylesheet"))));
    }

    @Test
    void searchEmployeeRendersReportDetailsAndImage() throws Exception {
        DtoEmployeeReport report = new DtoEmployeeReport();
        report.setEmpno(7788);
        report.setEname("SCOTT");
        report.setMgrName("JONES");
        report.setSal(new BigDecimal("3000.00"));
        report.setComm(new BigDecimal("0.00"));
        report.setDname("RESEARCH");
        report.setImg("scott.jpg");
        report.setTotalExpense(1234.5);

        when(employeeService.getEmployeeReport("SCOTT")).thenReturn(Optional.of(report));

        mockMvc.perform(get("/").param("ename", "SCOTT"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("/images/scott.jpg")))
                .andExpect(content().string(containsString("SCOTT")))
                .andExpect(content().string(containsString("JONES")))
                .andExpect(content().string(containsString("3000.00")))
                .andExpect(content().string(containsString("0.00")))
                .andExpect(content().string(containsString("RESEARCH")))
                .andExpect(content().string(containsString("1234.50")));
    }

    @Test
    void searchMissingEmployeeRendersError() throws Exception {
        when(employeeService.getEmployeeReport("UNKNOWN")).thenReturn(Optional.empty());

        mockMvc.perform(get("/").param("ename", "UNKNOWN"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Employee not found: UNKNOWN")));
    }
}
