package com.huge_data.project.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

// hiredate is not mapped: the page does not show it
@Entity
@Data
@Table(name = "emp")
@AllArgsConstructor
@NoArgsConstructor
public class Emp {

    @Id
    private Integer empno;

    private String ename;

    private String job;

    // the manager is another row of emp (self-join on emp.mgr).
    // Left out of toString/equals/hashCode: they would walk up the manager chain
    // and load lazy rows outside the database session.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mgr")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Emp mgr;

    private BigDecimal sal;

    private BigDecimal comm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deptno")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Dept dept;

    private String img;
}
