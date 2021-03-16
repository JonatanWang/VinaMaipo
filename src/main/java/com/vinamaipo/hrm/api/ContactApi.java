package com.vinamaipo.hrm.api;

import com.vinamaipo.hrm.domain.dto.*;
import com.vinamaipo.hrm.domain.model.Address;
import com.vinamaipo.hrm.domain.model.Role;
import com.vinamaipo.hrm.service.AddressService;
import com.vinamaipo.hrm.service.ContactService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@Tag(name = "Contact")
@RestController @RequestMapping(path = "api/v1/contact")
@RequiredArgsConstructor
public class ContactApi {

    private final ContactService contactService;
    private final AddressService addressService;

    @RolesAllowed(Role.CONTACT_ADMIN)
    @PostMapping
    public ContactView create(@RequestBody @Valid EditContactRequest request) {
        return contactService.create(request);
    }

    @RolesAllowed(Role.CONTACT_ADMIN)
    @PutMapping("{id}")
    public ContactView edit(@PathVariable String id, @RequestBody @Valid EditContactRequest request) {
        return contactService.update(new ObjectId(id), request);
    }

    @RolesAllowed(Role.CONTACT_ADMIN)
    @DeleteMapping("{id}")
    public ContactView delete(@PathVariable String id) {
        return contactService.delete(new ObjectId(id));
    }

    @GetMapping("{id}")
    public ContactView get(@PathVariable String id) {
        return contactService.getContact(new ObjectId(id));
    }

    @GetMapping("{id}/address")
    public ListResponse<AddressView> getAddresses(@PathVariable String id) {
        return new ListResponse<>(addressService.getContactAddresses(new ObjectId(id)));
    }

    @PostMapping("search")
    public ListResponse<ContactView> search(@RequestBody @Valid SearchRequest<SearchContactsQuery> request) {
        return new ListResponse<>(contactService.searchContacts(request.getPage(), request.getQuery()));
    }
}
