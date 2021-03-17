package com.vinamaipo.hrm.domain.mapper;

import com.vinamaipo.hrm.domain.dto.ContactView;
import com.vinamaipo.hrm.domain.dto.EditContactRequest;
import com.vinamaipo.hrm.domain.model.Contact;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", uses = ObjectIdMapper.class)
public abstract class ContactViewMapper {

    private UserViewMapper userViewMapper;

    @Autowired
    public void setUserViewMapper(UserViewMapper userViewMapper) {
        this.userViewMapper = userViewMapper;
    }

    public abstract ContactView toContactView(Contact contact);

    public abstract List<ContactView> toContactView(List<Contact> contacts);

    @AfterMapping
    protected void after(Contact contact, @MappingTarget ContactView contactView) {
        contactView.setCreator(userViewMapper.toUserViewById(contact.getCreatorId()));
    }
}
