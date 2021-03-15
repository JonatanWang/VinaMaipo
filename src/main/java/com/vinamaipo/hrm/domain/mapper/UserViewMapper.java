package com.vinamaipo.hrm.domain.mapper;

import com.vinamaipo.hrm.domain.dto.UserView;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Mapper(componentModel = "spring", uses = ObjectIdMapper.class)
public abstract class UserViewMapper {

    @Autowired
    private UserRepo userRepo;

    public abstract UserView toUserView(User user);

    public abstract List<UserView> toUserView(List<User> users);

    public UserView toUserViewById(ObjectId objectId) {

        return objectId == null ?
                null : toUserView(userRepo.getById(objectId));
    }
}
