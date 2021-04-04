package com.vinamaipo.hrm.service;

import com.vinamaipo.hrm.domain.dto.*;
import com.vinamaipo.hrm.domain.mapper.AddressEditMapper;
import com.vinamaipo.hrm.domain.mapper.AddressViewMapper;
import com.vinamaipo.hrm.domain.model.Address;
import com.vinamaipo.hrm.domain.model.Contact;
import com.vinamaipo.hrm.repository.AddressRepo;
import com.vinamaipo.hrm.repository.ContactRepo;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepo addressRepo;
    private final ContactRepo contactRepo;
    private final AddressEditMapper addressEditMapper;
    private final AddressViewMapper addressViewMapper;

    @Transactional
    public AddressView create(EditAddressRequest request) {
        var address = addressEditMapper.create(request);

        address = addressRepo.save(address);
        updateContacts(address);

        return addressViewMapper.toAddressView(address);
    }

    @Transactional
    public AddressView update(ObjectId id, EditAddressRequest request) {
        var address = addressRepo.getById(id);
        addressEditMapper.update(request, address);

        address = addressRepo.save(address);
        if (request.getContactId() != null) {
            updateContacts(address);
        }

        return addressViewMapper.toAddressView(address);
    }

    private void updateContacts(Address address) {
//        List<Contact> contacts = contactRepo.findAllById(address.getContactIds());
//        contacts.forEach(c -> c.getAddressIds().add(address.getId()));
//        contactRepo.saveAll(contacts);
    }

    @Transactional
    public AddressView delete(ObjectId id) {
        var address = addressRepo.getById(id);

        addressRepo.delete(address);

        return addressViewMapper.toAddressView(address);
    }

    public AddressView getAddress(ObjectId id) {
        var address = addressRepo.getById(id);
        return addressViewMapper.toAddressView(address);
    }

    public List<AddressView> getAddresses(Iterable<ObjectId> ids) {
        List<Address> addresses = addressRepo.findAllById(ids);
        return addressViewMapper.toAddressView(addresses);
    }

    public List<AddressView> getContactAddresses(ObjectId authorId) {
//        var contact = contactRepo.getById(authorId);
//        return addressViewMapper.toAddressView(addressRepo.findAllById(contact.getAddressIds()));
        return null;
    }

    public List<AddressView> searchAddresses(Page page, SearchAddressesQuery query) {

        return addressViewMapper.toAddressView(addressRepo.searchAddresses(page, query));
    }
}
