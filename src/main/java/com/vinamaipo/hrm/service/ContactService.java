package com.vinamaipo.hrm.service;

import com.vinamaipo.hrm.domain.dto.ContactView;
import com.vinamaipo.hrm.domain.dto.EditContactRequest;
import com.vinamaipo.hrm.domain.dto.Page;
import com.vinamaipo.hrm.domain.dto.SearchContactsQuery;
import com.vinamaipo.hrm.domain.mapper.ContactEditMapper;
import com.vinamaipo.hrm.domain.mapper.ContactViewMapper;
import com.vinamaipo.hrm.repository.AddressRepo;
import com.vinamaipo.hrm.repository.ContactRepo;
import com.vinamaipo.hrm.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepo contactRepo;
    private final AddressRepo addressRepo;
    private final UserRepo userRepo;
    private final ContactEditMapper contactEditMapper;
    private final ContactViewMapper contactViewMapper;

    @Transactional
    public ContactView create(EditContactRequest request) {
        var contact = contactEditMapper.create(request);

        contact = contactRepo.save(contact);

        return contactViewMapper.toContactView(contact);
    }

    @Transactional
    public ContactView update(ObjectId id, EditContactRequest request) {
        var contact = contactRepo.getById(id);
        contactEditMapper.update(request, contact);

        contact = contactRepo.save(contact);

        return contactViewMapper.toContactView(contact);
    }

    @Transactional
    public ContactView delete(ObjectId id) {
//        var contact = contactRepo.getById(id);
//
//        contactRepo.delete(contact);
//        addressRepo.deleteAll(addressRepo.findAllById(contact.getAddressIds()));
//
//        return contactViewMapper.toContactView(contact);
        return null;
    }

    public ContactView getContact(ObjectId id) {
        return contactViewMapper.toContactView(contactRepo.getById(id));
    }

    public List<ContactView> getContacts(Iterable<ObjectId> ids) {
        return contactViewMapper.toContactView(contactRepo.findAllById(ids));
    }

    public List<ContactView> getAddressContacts(ObjectId addressId) {
        var address = addressRepo.getById(addressId);
        return contactViewMapper.toContactView(contactRepo.findAllById(new ArrayList<>(Collections.singleton(address.getContactId()))));

    }

    public List<ContactView> searchContacts(Page page, SearchContactsQuery query) {

        return contactViewMapper.toContactView(contactRepo.searchContacts(page, query));
    }

    public List<ContactView> searchContacts(Page page) {

        return contactViewMapper.toContactView(contactRepo.searchContacts(page));
    }

    public List<ContactView> getUserContacts(ObjectId userId) {

        return contactViewMapper.toContactView(contactRepo.findByUserId(userId));
    }
}

