package com.example.employee.repository;

import com.example.employee.model.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, String> {

    List findByDepartmentId(Long departmentId);
    List findByOrganizationId(Long organizationId);

}
