package com.vinamaipo.hrm;

import com.vinamaipo.hrm.domain.dto.CreateUserRequest;
import com.vinamaipo.hrm.domain.model.Role;
import com.vinamaipo.hrm.service.UserService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Set;

@Component
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final List<String> usernames = List.of(
            "par.tjarnberg@cygni.se",
            "alexander.urban@cygni.se",
            "zhengyu.wang@cygni.se"
    );
    private final List<String> fullNames = List.of(
            "Pär Tjärnberg",
            "Alexander Urban",
            "Zhengyu Wang"
    );
    private final List<String> roles = List.of(
            Role.USER_ADMIN,
            Role.CONTACT_ADMIN,
            Role.ADDRESS_ADMIN
    );

    private final String password = "pass";

    private final UserService userService;

    public DatabaseInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        for (int i = 0; i < usernames.size(); ++ i) {
            CreateUserRequest request = new CreateUserRequest();
            request.setUsername(usernames.get(i));
            request.setFullName(fullNames.get(i));
            request.setPassword(password);
            request.setRePassword(password);
            request.setAuthorities(Set.of(roles.get(i)));

            userService.upsert(request);
        }
    }
}

