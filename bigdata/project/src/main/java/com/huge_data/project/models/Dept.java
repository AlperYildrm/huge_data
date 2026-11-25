package com.huge_data.project.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "dept")
@AllArgsConstructor
@NoArgsConstructor
public class Dept {

    @Id
    private Integer deptno;

    private String dname;

    private String loc;
}
