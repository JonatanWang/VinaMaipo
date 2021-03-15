package com.vinamaipo.hrm.domain.mapper;

import com.vinamaipo.hrm.domain.dto.EditContactRequest;
import com.vinamaipo.hrm.domain.model.Contact;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = ObjectIdMapper.class)
public interface ContactEditMapper {

    Contact create(EditContactRequest request);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(EditContactRequest request, @MappingTarget Contact contact);
}
