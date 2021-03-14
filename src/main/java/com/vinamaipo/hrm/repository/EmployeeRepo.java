package com.vinamaipo.hrm.repository;

import com.vinamaipo.hrm.domain.model.Employee;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.rmi.NotBoundException;
import java.util.List;

@Repository
public interface EmployeeRepo extends MongoRepository<Employee, ObjectId>, EmployeeRepoCustom {

    public List<Employee> findByNameContaining(String name);

    default Employee getById(ObjectId id) {
        return findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException(Employee.class, id));
    }
}
