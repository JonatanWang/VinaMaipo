package com.vinamaipo.hrm.domain.mapper;

import com.vinamaipo.hrm.domain.dto.AddressView;
import com.vinamaipo.hrm.domain.model.Address;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Mapper(componentModel = "spring", uses = ObjectIdMapper.class)
public abstract class AddressViewMapper {

    private UserViewMapper userViewMapper;

    @Autowired
    public void setUserViewMapper(UserViewMapper userViewMapper) {
        this.userViewMapper = userViewMapper;
    }

    public abstract AddressView toAddressView(Address address);

    public abstract List<AddressView> toAddressView(List<Address> addresses);

    @AfterMapping
    protected void after(Address address, @MappingTarget AddressView addressView) {

        addressView.setCreator(userViewMapper.toUserViewById(address.getCreatorId()));
    }

}
