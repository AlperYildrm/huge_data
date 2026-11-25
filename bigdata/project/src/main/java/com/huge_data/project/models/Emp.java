package com.huge_data.project.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

// hiredate is not mapped: the page does not show it
@Entity
@Table(name = "emp")
public class Emp {

    @Id
    private Integer empno;

    private String ename;

    private String job;

    // the manager is another row of emp (self-join on emp.mgr)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mgr")
    private Emp mgr;

    private BigDecimal sal;

    private BigDecimal comm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deptno")
    private Dept dept;

    private String img;

    public Integer getEmpno() {
        return empno;
    }

    public void setEmpno(Integer empno) {
        this.empno = empno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Emp getMgr() {
        return mgr;
    }

    public void setMgr(Emp mgr) {
        this.mgr = mgr;
    }

    public BigDecimal getSal() {
        return sal;
    }

    public void setSal(BigDecimal sal) {
        this.sal = sal;
    }

    public BigDecimal getComm() {
        return comm;
    }

    public void setComm(BigDecimal comm) {
        this.comm = comm;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
