package com.huge_data.project.controllers;

import com.huge_data.project.dto.DtoEmployeeReport;
import com.huge_data.project.services.IEmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class EmployeeController {

    private final IEmployeeService employeeService;

    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String index(@RequestParam(value = "ename", required = false) String ename, Model model) {
        if (ename != null && !ename.isBlank()) {
            Optional<DtoEmployeeReport> report = employeeService.getEmployeeReport(ename);
            if (report.isPresent()) {
                model.addAttribute("emp", report.get());
            } else {
                model.addAttribute("error", "Employee not found: " + ename);
            }
        }
        return "index";
    }
}
