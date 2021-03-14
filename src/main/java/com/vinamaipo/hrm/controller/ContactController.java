package com.vinamaipo.hrm.controller;

import com.vinamaipo.hrm.domain.model.Contact;
import com.vinamaipo.hrm.repository.ContactRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class ContactController {

    @Autowired
    private final ContactRepo contactRepo;

    public ContactController(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    @PostMapping("/employees")
    public ResponseEntity<Contact> saveEmployee(@RequestBody Contact contact) {
        try {
            var _employee =  contactRepo.save(contact);

            return new ResponseEntity<>(_employee, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Contact> getEmployeeById(@PathVariable("id") String id) {
        var employeeData = contactRepo.findById(id);

        return employeeData.isPresent() ? new ResponseEntity<>(employeeData.get(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Contact>> findAllEmployees(@RequestParam(required = false) String name) {
        try {
            var employees = new ArrayList<Contact>();

            if (name == null)
                contactRepo.findAll().forEach(employees::add);
            else
                contactRepo.findByNameContaining(name).forEach(employees::add);

            if (employees.isEmpty()) {

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Contact> updateEmployee(@PathVariable("id") String id, @RequestBody Contact contact) {
        var employeeData = contactRepo.findById(id);
        if (employeeData.isPresent()) {
            var _employee = employeeData.get();
            _employee.setName(contact.getName());
            _employee.setAddress(contact.getAddress());

            return new ResponseEntity<>(contactRepo.save(_employee), HttpStatus.OK);
        } else {

            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable("id") String id) {
        try {
            contactRepo.deleteById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/employees")
    public ResponseEntity<HttpStatus> deleteAllEmployees() {
        try {
            contactRepo.deleteAll();

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
