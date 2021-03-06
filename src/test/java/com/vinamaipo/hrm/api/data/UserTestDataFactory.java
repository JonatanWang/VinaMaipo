package com.vinamaipo.hrm.api.data;

import com.vinamaipo.hrm.domain.dto.CreateUserRequest;
import com.vinamaipo.hrm.domain.dto.UserView;
import com.vinamaipo.hrm.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Service
public class UserTestDataFactory {

    @Autowired
    private UserService userService;

    public UserView createUser(String username,
                               String fullname,
                               String password) {
        var createRequest = new CreateUserRequest();
        createRequest.setUsername(username);
        createRequest.setFullname(fullname);
        createRequest.setPassword(password);
        createRequest.setRePassword(password);

        var userView = userService.create(createRequest);

        assertNotNull(userView.getId(), "User id must not be null!");
        assertEquals(fullname, userView.getFullname(), "User name update isn't applied!");

        return userView;
    }

    public UserView createUser(String username,
                               String fullname) {
        return createUser(username, fullname, "Test12345_");
    }

    public void deleteUser(String id) {
        userService.delete(new ObjectId(id));
    }
}
