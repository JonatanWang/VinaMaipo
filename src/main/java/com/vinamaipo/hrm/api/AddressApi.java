package com.vinamaipo.hrm.api;

import com.vinamaipo.hrm.domain.dto.*;
import com.vinamaipo.hrm.domain.model.Role;
import com.vinamaipo.hrm.service.AddressService;
import com.vinamaipo.hrm.service.ContactService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@Tag(name = "Address")
@RestController @RequestMapping(path = "api/v1/address")
@RequiredArgsConstructor
public class AddressApi {

    private final AddressService addressService;
    private final ContactService contactService;

    @RolesAllowed({Role.ADDRESS_ADMIN, Role.USER_ADMIN})
    @PostMapping
    public AddressView create(@RequestBody @Valid EditAddressRequest request) {
        return addressService.create(request);
    }

    @RolesAllowed({Role.ADDRESS_ADMIN, Role.USER_ADMIN})
    @PutMapping("{id}")
    public AddressView edit(@PathVariable String id, @RequestBody @Valid EditAddressRequest request) {
        return addressService.update(new ObjectId(id), request);
    }

    @RolesAllowed({Role.ADDRESS_ADMIN, Role.USER_ADMIN})
    @DeleteMapping("{id}")
    public AddressView delete(@PathVariable String id) {
        return addressService.delete(new ObjectId(id));
    }

    @GetMapping("{id}")
    public AddressView get(@PathVariable String id) {
        return addressService.getAddress(new ObjectId(id));
    }

    @GetMapping("{id}/contact")
    public ListResponse<ContactView> getAuthors(@PathVariable String id) {
        return new ListResponse<>(contactService.getAddressContacts(new ObjectId(id)));
    }

    @PostMapping("search")
    public ListResponse<AddressView> search(@RequestBody @Valid SearchRequest<SearchAddressesQuery> request) {
        return new ListResponse<>(addressService.searchAddresses(request.getPage(), request.getQuery()));
    }
}
