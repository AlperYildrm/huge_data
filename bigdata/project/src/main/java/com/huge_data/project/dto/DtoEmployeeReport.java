package com.huge_data.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

// what the page shows: image, ename, manager name, salary, commission, department, total expense
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoEmployeeReport {

    private Integer empno;
    private String ename;
    private String mgrName;
    private BigDecimal sal;
    private BigDecimal comm;
    private String dname;
    private String img;
    private double totalExpense;
}
