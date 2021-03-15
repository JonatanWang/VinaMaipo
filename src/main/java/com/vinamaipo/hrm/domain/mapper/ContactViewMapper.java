package com.vinamaipo.hrm.domain.mapper;

import com.vinamaipo.hrm.domain.dto.EditContactRequest;
import com.vinamaipo.hrm.domain.model.Contact;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", uses = ObjectIdMapper.class)
public interface ContactViewMapper {

    Contact create(EditContactRequest request);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    void update(EditContactRequest request, @MappingTarget Contact contact);
}
