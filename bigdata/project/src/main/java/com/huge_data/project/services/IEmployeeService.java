package com.huge_data.project.services;

import com.huge_data.project.dto.DtoEmployeeReport;

import java.util.Optional;

public interface IEmployeeService {

    Optional<DtoEmployeeReport> getEmployeeReport(String ename);
}
