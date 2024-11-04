package com.example.springproject.DAO;

import com.example.springproject.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByAdminId(Integer adminId);
}
