package com.vinamaipo.hrm.api;

import com.vinamaipo.hrm.domain.dto.*;
import com.vinamaipo.hrm.domain.model.Role;
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

@Tag(name = "UserAdmin")
@RestController @RequestMapping(path = "api/v1/admin/user")
@RequiredArgsConstructor
public class UserAdminApi {

    private final UserService userService;

    @RolesAllowed({Role.USER, Role.ADMIN})
    @PostMapping
    public UserView create(@RequestBody @Valid CreateUserRequest request) {
        return userService.create(request);
    }

    @RolesAllowed(Role.ADMIN)
    @PutMapping("{id}")
    public UserView update(@PathVariable String id, @RequestBody @Valid UpdateUserRequest request) {
        return userService.update(new ObjectId(id), request);
    }

    @RolesAllowed(Role.ADMIN)
    @DeleteMapping("{id}")
    public UserView delete(@PathVariable String id) {
        return userService.delete(new ObjectId(id));
    }

    @RolesAllowed({Role.USER, Role.ADMIN})
    @GetMapping("{id}")
    public UserView get(@PathVariable String id) {
        return userService.getUser(new ObjectId(id));
    }

    @RolesAllowed({Role.USER,Role.ADMIN})
    @PostMapping("search")
    public ListResponse<UserView> search(@RequestBody SearchRequest<SearchUsersQuery> request) {
        return new ListResponse<>(userService.searchUsers(request.getPage(), request.getQuery()));
    }

    @RolesAllowed({Role.USER, Role.ADMIN})
    @GetMapping("all")
    public ListResponse<UserView> getAllUsers() {
        return new ListResponse<>(userService.searchUsers(new Page()));
    }
}