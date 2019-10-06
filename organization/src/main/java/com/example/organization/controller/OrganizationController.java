package com.example.organization.controller;

import com.example.organization.client.DepartmentClient;
import com.example.organization.client.EmployeeClient;
import com.example.organization.model.Organization;
import com.example.organization.repository.OrganizationRepository;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class OrganizationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationController.class);

    @Autowired
    OrganizationRepository repository;

    @Autowired
    DepartmentClient departmentClient;

    @Autowired
    EmployeeClient employeeClient;

    @ApiOperation(value ="Add organization")
    @PostMapping("/")
    public Organization add(@RequestBody Organization organization) {
        LOGGER.info("Organization add: {}", organization);
        return repository.save(organization);
    }

    @ApiOperation(value ="Find organization by id")
    @GetMapping("/{id}")
    public Organization findById(@PathVariable("id") String id) {
        LOGGER.info("Organization find: id={}", id);
        return repository.findById(id).get();
    }

    @GetMapping("/")
    public Iterable findAll() {
        LOGGER.info("Organization find");
        return repository.findAll();
    }

    @GetMapping("/{id}/with-departments")
    public Organization findByIdWithDepartments(@PathVariable("id") String id) {
        LOGGER.info("Organization find: id={}", id);
        Optional<Organization> organization = repository.findById(id);
        if (organization.isPresent()) {
            Organization o = organization.get();
            o.setDepartments(departmentClient.findByOrganization(o.getId()));
            return o;
        } else {
            return null;
        }
    }

    @GetMapping("/{id}/with-departments-and-employees")
    public Organization findByIdWithDepartmentsAndEmployees(@PathVariable("id") String id) {
        LOGGER.info("Organization find: id={}", id);
        Optional<Organization> organization = repository.findById(id);
        if (organization.isPresent()) {
            Organization o = organization.get();
            o.setDepartments(departmentClient.findByOrganizationWithEmployees(o.getId()));
            return o;
        } else {
            return null;
        }
    }

    @GetMapping("/{id}/with-employees")
    public Organization findByIdWithEmployees(@PathVariable("id") String id) {
        LOGGER.info("Organization find: id={}", id);
        Optional<Organization> organization = repository.findById(id);
        if (organization.isPresent()) {
            Organization o = organization.get();
            o.setEmployees(employeeClient.findByOrganization(o.getId()));
            return o;
        } else {
            return null;
        }
    }
}
