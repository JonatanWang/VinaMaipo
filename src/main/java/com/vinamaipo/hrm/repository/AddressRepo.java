package com.vinamaipo.hrm.repository;

import com.vinamaipo.hrm.domain.dto.Page;
import com.vinamaipo.hrm.domain.dto.SearchAddressesQuery;
import com.vinamaipo.hrm.domain.exception.NotFoundException;
import com.vinamaipo.hrm.domain.model.Address;
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

@Repository
public interface AddressRepo extends MongoRepository<Address, ObjectId>, BookRepoCustom {

    default Address getById(ObjectId id) {
        return findById(id).orElseThrow(() -> new NotFoundException(Address.class, id));
    }

    List<Address> findAllById(Iterable<ObjectId> ids);

}

interface BookRepoCustom {

    List<Address> searchBooks(Page page, SearchAddressesQuery query);

}

@RequiredArgsConstructor
class BookRepoCustomImpl implements BookRepoCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Address> searchBooks(Page page, SearchAddressesQuery query) {
        List<AggregationOperation> operations = new ArrayList<>();

        List<Criteria> criteriaList = new ArrayList<>();
        if (!StringUtils.isEmpty(query.getId())) {
            criteriaList.add(Criteria.where("id").is(new ObjectId(query.getId())));
        }
        if (!StringUtils.isEmpty(query.getCreatorId())) {
            criteriaList.add(Criteria.where("creatorId").is(new ObjectId(query.getCreatorId())));
        }
        if (query.getCreatedAtStart() != null) {
            criteriaList.add(Criteria.where("createdAt").gte(query.getCreatedAtStart()));
        }
        if (query.getCreatedAtEnd() != null) {
            criteriaList.add(Criteria.where("createdAt").lt(query.getCreatedAtEnd()));
        }
        if (!StringUtils.isEmpty(query.getStreet())) {
            criteriaList.add(Criteria.where("street").regex(query.getStreet(), "i"));
        }

        if (!criteriaList.isEmpty()) {
            Criteria addressCriteria = new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
            operations.add(match(addressCriteria));
        }

        criteriaList = new ArrayList<>();
        if (!StringUtils.isEmpty(query.getContactId())) {
            criteriaList.add(Criteria.where("contact._id").is(new ObjectId(query.getContactId())));
        }
        if (!StringUtils.isEmpty(query.getContactName())) {
            criteriaList.add(Criteria.where("contact.name").regex(query.getContactName(), "i"));
        }
        if (!criteriaList.isEmpty()) {
            Criteria authorCriteria = new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
            operations.add(lookup("contacts", "contactIds", "_id", "contact"));
            operations.add(unwind("contact", false));
            operations.add(match(authorCriteria));
        }

        operations.add(sort(Sort.Direction.DESC, "createdAt"));
        operations.add(skip((page.getNumber() - 1) * page.getLimit()));
        operations.add(limit(page.getLimit()));

        TypedAggregation<Address> aggregation = newAggregation(Address.class, operations);
        System.out.println(aggregation.toString());
        AggregationResults<Address> results = mongoTemplate.aggregate(aggregation, Address.class);

        return results.getMappedResults();
    }
}

