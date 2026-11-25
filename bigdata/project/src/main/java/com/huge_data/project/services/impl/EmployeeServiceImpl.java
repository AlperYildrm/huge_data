package com.huge_data.project.services.impl;

import com.huge_data.project.dto.DtoEmployeeReport;
import com.huge_data.project.models.Emp;
import com.huge_data.project.repositories.EmpRepository;
import com.huge_data.project.services.IEmployeeService;
import com.huge_data.project.services.IExpenseSparkService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmpRepository empRepository;
    private final IExpenseSparkService expenseSparkService;

    public EmployeeServiceImpl(EmpRepository empRepository, IExpenseSparkService expenseSparkService) {
        this.empRepository = empRepository;
        this.expenseSparkService = expenseSparkService;
    }

    @Override
    public Optional<DtoEmployeeReport> getEmployeeReport(String ename) {
        Optional<Emp> found = empRepository.findByEnameWithMgrAndDept(ename.trim());
        if (found.isEmpty()) {
            return Optional.empty();
        }

        Emp emp = found.get();
        DtoEmployeeReport report = new DtoEmployeeReport();
        report.setEmpno(emp.getEmpno());
        report.setEname(emp.getEname());
        report.setMgrName(emp.getMgr() == null ? "NONE" : emp.getMgr().getEname());
        report.setSal(emp.getSal());
        report.setComm(emp.getComm() == null ? BigDecimal.ZERO.setScale(2) : emp.getComm());
        report.setDname(emp.getDept().getDname());
        report.setImg(emp.getImg());
        report.setTotalExpense(expenseSparkService.getTotalExpense(emp.getEmpno()));
        return Optional.of(report);
    }
}
