package com.huge_data.project;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {

    private final JdbcTemplate jdbcTemplate;
    private final ExpenseSparkService expenseSparkService;

    public EmployeeController(JdbcTemplate jdbcTemplate, ExpenseSparkService expenseSparkService) {
        this.jdbcTemplate = jdbcTemplate;
        this.expenseSparkService = expenseSparkService;
    }

    @GetMapping("/")
    public String index(@RequestParam(value = "ename", required = false) String ename, Model model) {
        if (ename != null && !ename.isBlank()) {
            String sql = """
                SELECT e.empno,
                       e.ename,
                       COALESCE(m.ename, 'NONE') AS mgr_name,
                       e.sal,
                       COALESCE(e.comm, 0) AS comm,
                       d.dname,
                       e.img
                FROM emp e
                LEFT JOIN emp m ON e.mgr = m.empno
                JOIN dept d ON e.deptno = d.deptno
                WHERE LOWER(e.ename) = LOWER(?)
                """;

            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, ename.trim());
            if (!rows.isEmpty()) {
                Map<String, Object> emp = rows.get(0);
                int empno = ((Number) emp.get("empno")).intValue();

                double totalExpense = expenseSparkService.getTotalExpense(empno);
                emp.put("total_expense", totalExpense);

                model.addAttribute("emp", emp);
            } else {
                model.addAttribute("error", "Employee not found: " + ename);
            }
        }
        return "index";
    }

    @GetMapping(value = "/images/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage(@PathVariable String fileName) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://localhost:9000");
        FileSystem fs = FileSystem.get(conf);
        try (FSDataInputStream in = fs.open(new Path("/swe307/images/" + fileName))) {
            return in.readAllBytes();
        }
    }
}