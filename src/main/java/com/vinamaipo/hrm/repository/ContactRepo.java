package com.vinamaipo.hrm.repository;

import com.mongodb.internal.operation.AggregateOperation;
import com.vinamaipo.hrm.domain.dto.Page;
import com.vinamaipo.hrm.domain.dto.SearchContactsQuery;
import com.vinamaipo.hrm.domain.exception.NotFoundException;
import com.vinamaipo.hrm.domain.model.Contact;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ContactRepo extends MongoRepository<Contact, ObjectId> {

    public List<Contact> findByNameContaining(String name);

    default Contact getById(ObjectId id) {
        return findById(id).orElseThrow(() -> new NotFoundException(Contact.class, id));
    }

    List<Contact> findAllById(Iterable<ObjectId> ids);
}

interface ContactRepoCustom {

    List<Contact> searchContacts(Page page, SearchContactsQuery query);
}

@RequiredArgsConstructor
class ContactRepoCustomImpl implements ContactRepoCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Contact> searchContacts(Page page, SearchContactsQuery query) {

        var operations = new ArrayList<AggregateOperation>();
        var criteriaList = new ArrayList<Criteria>();

        if (!StringUtils.isEmpty(query.getId())) {
            criteriaList.add(Criteria.where("id").is(new ObjectId(query.getId())));
        }

        if (!StringUtils.containsWhitespace(query.getCreatorId())) {
            criteriaList.add(Criteria.where("creatorId").is(new ObjectId(query.getCreatorId())));
        }
        return null;
    }
}