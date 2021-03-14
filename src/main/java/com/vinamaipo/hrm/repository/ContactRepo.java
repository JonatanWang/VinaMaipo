package com.vinamaipo.hrm.repository;

import com.vinamaipo.hrm.domain.model.Contact;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepo extends MongoRepository<Contact, ObjectId>, EmployeeRepoCustom {

    public List<Contact> findByNameContaining(String name);

    default Contact getById(ObjectId id) {
        return findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException(Contact.class, id));
    }
}
