package com.vinamaipo.hrm.api;

import com.vinamaipo.hrm.domain.dto.*;
import com.vinamaipo.hrm.domain.model.Role;
import com.vinamaipo.hrm.service.ContactService;
import com.vinamaipo.hrm.service.UserService;
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

@Tag(name = "User")
@RestController @RequestMapping(path = "api/v1/user")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;
    private final ContactService contactService;

    @RolesAllowed({Role.ADMIN})
    @PostMapping
    public UserView create(@RequestBody @Valid CreateUserRequest request) {
        return userService.create(request);
    }

    @RolesAllowed({Role.ADMIN})
    @PutMapping("/admin/{id}")
    public UserView update(@PathVariable String id, @RequestBody @Valid UpdateUserRequest request) {
        return userService.update(new ObjectId(id), request);
    }

    @RolesAllowed({Role.ADMIN})
    @DeleteMapping("/admin/{id}")
    public UserView delete(@PathVariable String id) {
        return userService.delete(new ObjectId(id));
    }

    @RolesAllowed({Role.ADMIN, Role.USER})
    @GetMapping("{id}")
    public UserView get(@PathVariable String id) {
        return userService.getUser(new ObjectId(id));
    }

    @RolesAllowed({Role.ADMIN, Role.USER})
    @PostMapping("search")
    public ListResponse<UserView> search(@RequestBody SearchRequest<SearchUsersQuery> request) {
        return new ListResponse<>(userService.searchUsers(request.getPage(), request.getQuery()));
    }

    @RolesAllowed({Role.ADMIN, Role.USER})
    @GetMapping("all")
    public ListResponse<UserView> getAllUsers() {
        return new ListResponse<>(userService.searchUsers(new Page()));
    }

    @RolesAllowed({Role.ADMIN, Role.USER})
    @GetMapping("{id}/contact")
    public ListResponse<ContactView> getContacts(@PathVariable String id) {
        return new ListResponse<>(contactService.getUserContacts((new ObjectId(id))));
    }
}