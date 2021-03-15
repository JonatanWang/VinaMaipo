package com.vinamaipo.hrm.controller;

import com.vinamaipo.hrm.domain.model.Contact;
import com.vinamaipo.hrm.repository.ContactRepo;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ContactController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ContactController.class);
    @Autowired
    private final ContactRepo contactRepo;

    public ContactController(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    @PostMapping("/contacts")
    public ResponseEntity<Contact> saveContact(@RequestBody Contact contact) {
        try {
            var _contact =  contactRepo.save(contact);

            return new ResponseEntity<>(_contact, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable("id") ObjectId id) {
        var contactData = contactRepo.findById(id);

        return contactData.isPresent() ? new ResponseEntity<>(contactData.get(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> findAllContacts(@RequestParam(required = false) String name) {
        try {
            var contacts = new ArrayList<Contact>();

            if (name == null)
                contactRepo.findAll().forEach(contacts::add);
            else
                contactRepo.findByNameContaining(name).forEach(contacts::add);

            if (contacts.isEmpty()) {

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(contacts, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/contacts/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable("id") ObjectId id, @RequestBody Contact contact) {
        var contactData = contactRepo.findById(id);
        if (contactData.isPresent()) {
            var _contact = contactData.get();
            _contact.setName(contact.getName());
            _contact.setAddress(contact.getAddress());

            return new ResponseEntity<>(contactRepo.save(_contact), HttpStatus.OK);
        } else {

            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable("id") ObjectId id) {
        try {
            contactRepo.deleteById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/contacts")
    public ResponseEntity<HttpStatus> deleteAllContacts() {
        try {
            contactRepo.deleteAll();

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
