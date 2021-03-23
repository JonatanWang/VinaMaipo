package com.vinamaipo.hrm.repository;

import com.mongodb.internal.operation.AggregateOperation;
import com.vinamaipo.hrm.domain.dto.Page;
import com.vinamaipo.hrm.domain.dto.SearchContactsQuery;
import com.vinamaipo.hrm.domain.exception.NotFoundException;
import com.vinamaipo.hrm.domain.model.Contact;
import com.vinamaipo.hrm.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Repository
public interface ContactRepo extends MongoRepository<Contact, ObjectId>, ContactRepoCustom {

    public List<Contact> findByNameContaining(String name);

    default Contact getById(ObjectId id) {
        return findById(id).orElseThrow(() -> new NotFoundException(Contact.class, id));
    }

    List<Contact> findAllById(Iterable<ObjectId> ids);
}

interface ContactRepoCustom {

    List<Contact> searchContacts(Page page, SearchContactsQuery query);
    List<Contact> searchContacts(Page page);
}

@RequiredArgsConstructor
class ContactRepoCustomImpl implements ContactRepoCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Contact> searchContacts(Page page, SearchContactsQuery query) {

        var  operations = new ArrayList<AggregationOperation>();
        var criteriaList = new ArrayList<Criteria>();

        if (!StringUtils.isEmpty(query.getId())) {
            criteriaList.add(Criteria.where("id").is(new ObjectId(query.getId())));
        }

        if (!StringUtils.containsWhitespace(query.getCreatorId())) {
            criteriaList.add(Criteria.where("creatorId").is(new ObjectId(query.getCreatorId())));
        }
        if (query.getCreatedAtStart() != null) {
            criteriaList.add(Criteria.where("createdAt").gte(query.getCreatedAtStart()));
        }
        if (query.getCreatedAtEnd() != null) {
            criteriaList.add(Criteria.where("createdAt").lt(query.getCreatedAtEnd()));
        }

        if (!criteriaList.isEmpty()) {
            Criteria contactCriteria = new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
            operations.add(match(contactCriteria));
        }

        criteriaList = new ArrayList<>();
        if (!StringUtils.isEmpty(query.getAddressId())) {
            criteriaList.add(Criteria.where("address._id").is(new ObjectId(query.getAddressId())));
        }
        if (!StringUtils.isEmpty(query.getAddressStreet())) {
            criteriaList.add(Criteria.where("address.street").regex(query.getAddressStreet(), "i"));
        }
        if (!criteriaList.isEmpty()) {
            Criteria addressCriteria = new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
            operations.add(lookup("addresses", "addressIds", "_id", "address"));
            operations.add(unwind("address", false));
            operations.add(match(addressCriteria));
        }

        operations.add(sort(Sort.Direction.DESC, "createdAt"));
        operations.add(skip((page.getNumber() - 1) * page.getLimit()));
        operations.add(limit(page.getLimit()));

        TypedAggregation<Contact> aggregation = newAggregation(Contact.class, operations);
        AggregationResults<Contact> results = mongoTemplate.aggregate(aggregation, Contact.class);

        return results.getMappedResults();
    }

    @Override
    public List<Contact> searchContacts(Page page) {
        List<AggregationOperation> operations = new ArrayList<>();
        var criteriaList = new ArrayList<Criteria>();
        Criteria contactCriteria = new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));

        Criteria addressCriteria = new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
        operations.add(lookup("addresses", "addressIds", "_id", "address"));
        operations.add(unwind("address", false));
        operations.add(match(addressCriteria));

        operations.add(match(contactCriteria));
        operations.add(sort(Sort.Direction.DESC, "createdAt"));
        operations.add(skip((page.getNumber() - 1) * page.getLimit()));
        operations.add(limit(page.getLimit()));

        TypedAggregation<Contact> aggregation = newAggregation(Contact.class, operations);
        AggregationResults<Contact> results = mongoTemplate.aggregate(aggregation, Contact.class);

        return results.getMappedResults();
    }
}