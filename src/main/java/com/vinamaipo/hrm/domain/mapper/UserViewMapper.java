package com.vinamaipo.hrm.domain.mapper;

import com.vinamaipo.hrm.domain.dto.UserView;
import com.vinamaipo.hrm.domain.model.User;
import com.vinamaipo.hrm.repository.UserRepo;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Mapper(componentModel = "spring", uses = {ObjectIdMapper.class})
public abstract class UserViewMapper {

    @Autowired
    private UserRepo userRepo;

    public abstract UserView toUserView(User user);

    public abstract List<UserView> toUserView(List<User> users);

    public UserView toUserViewById(ObjectId id) {
        if (id == null) {
            return null;
        }
        return toUserView(userRepo.getById(id));
    }
}
