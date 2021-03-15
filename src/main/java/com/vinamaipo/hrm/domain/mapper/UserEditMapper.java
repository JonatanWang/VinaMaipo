package com.vinamaipo.hrm.domain.mapper;

import com.vinamaipo.hrm.domain.dto.CreateUserRequest;
import com.vinamaipo.hrm.domain.dto.UpdateUserRequest;
import com.vinamaipo.hrm.domain.model.Role;
import com.vinamaipo.hrm.domain.model.User;
import org.mapstruct.*;

import javax.management.relation.RoleStatus;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Mapper(componentModel = "spring", uses = ObjectIdMapper.class)
public abstract class UserEditMapper {

    @Mapping(target = "authorities", ignore = true)
    public abstract User create(CreateUserRequest request);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "authorities", ignore = true)
    public abstract void update(UpdateUserRequest request,
                                @MappingTarget User user);

    @AfterMapping
    protected void afterCreate(CreateUserRequest request,
                               @MappingTarget User user) {
        if (request.getAuthorities() != null) {
            user.setAuthorities(request
                    .getAuthorities()
                    .stream()
                    .map(Role::new)
                    .collect(toSet()));
        }
    }

    @AfterMapping
    protected void afterUpdate(UpdateUserRequest request,
                               @MappingTarget User user) {
        if (request.getAuthorities() != null) {
            user.setAuthorities(request
            .getAuthorities()
            .stream()
            .map(Role::new)
            .collect(toSet()));
        }
    }
}
