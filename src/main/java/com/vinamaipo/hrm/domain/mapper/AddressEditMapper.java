package com.vinamaipo.hrm.domain.mapper;

import com.vinamaipo.hrm.domain.dto.*;
import com.vinamaipo.hrm.domain.model.Address;
import com.vinamaipo.hrm.domain.model.Role;
import com.vinamaipo.hrm.domain.model.User;
import org.mapstruct.*;

import static java.util.stream.Collectors.toSet;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", uses = ObjectIdMapper.class)
public interface AddressEditMapper {

    Address create(EditAddressRequest request);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(EditAddressRequest request, @MappingTarget Address address);
}
