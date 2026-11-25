package com.huge_data.project.repositories;

import com.huge_data.project.models.Emp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpRepository extends JpaRepository<Emp, Integer> {

    // one SQL query: emp LEFT JOIN emp (manager) JOIN dept
    @Query("SELECT e FROM Emp e LEFT JOIN FETCH e.mgr JOIN FETCH e.dept WHERE UPPER(e.ename) = UPPER(:ename)")
    Optional<Emp> findByEnameWithMgrAndDept(@Param("ename") String ename);
}
