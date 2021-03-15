package com.vinamaipo.hrm.domain.mapper;

import com.vinamaipo.hrm.domain.dto.EditAddressRequest;
import com.vinamaipo.hrm.domain.model.Address;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = ObjectIdMapper.class)
public interface AddressEditMapper {

    Address create(EditAddressRequest request);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(EditAddressRequest request, @MappingTarget Address address);
}
