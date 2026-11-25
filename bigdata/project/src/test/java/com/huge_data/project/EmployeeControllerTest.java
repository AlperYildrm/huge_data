package com.huge_data.project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@Import(EmployeeControllerTest.TestConfig.class)
class EmployeeControllerTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        ExpenseSparkService expenseSparkService() {
            return new ExpenseSparkService(null) {
                @Override
                public double getTotalExpense(int empno) {
                    return 1234.5;
                }
            };
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JdbcTemplate jdbcTemplate;

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
        Map<String, Object> emp = new HashMap<>();
        emp.put("empno", 7788);
        emp.put("ename", "SCOTT");
        emp.put("mgr_name", "JONES");
        emp.put("sal", new BigDecimal("3000.00"));
        emp.put("comm", new BigDecimal("0.00"));
        emp.put("dname", "RESEARCH");
        emp.put("img", "scott.jpg");

        when(jdbcTemplate.queryForList(anyString(), eq("SCOTT"))).thenReturn(List.of(emp));

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
        when(jdbcTemplate.queryForList(anyString(), eq("UNKNOWN"))).thenReturn(List.of());

        mockMvc.perform(get("/").param("ename", "UNKNOWN"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Employee not found: UNKNOWN")));
    }
}
