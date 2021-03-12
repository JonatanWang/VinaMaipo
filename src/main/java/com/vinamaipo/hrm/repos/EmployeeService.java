package com.vinamaipo.hrm.repos;

import com.vinamaipo.hrm.domain.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeService extends MongoRepository<Employee, String> {

    public List<Employee> findByNameContaining(String name);
}
