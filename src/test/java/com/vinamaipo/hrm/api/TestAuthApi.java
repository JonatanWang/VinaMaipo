package com.vinamaipo.hrm.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinamaipo.hrm.api.data.UserTestDataFactory;
import com.vinamaipo.hrm.domain.dto.AuthRequest;
import com.vinamaipo.hrm.domain.dto.CreateUserRequest;
import com.vinamaipo.hrm.domain.dto.UserView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.vinamaipo.hrm.util.JsonHelper.fromJson;
import static com.vinamaipo.hrm.util.JsonHelper.toJson;
import static java.lang.System.currentTimeMillis;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestAuthApi {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final UserTestDataFactory userTestDataFactory;
    private final String password = "pass";

    @Autowired
    public TestAuthApi(MockMvc mockMvc, ObjectMapper objectMapper, UserTestDataFactory userTestDataFactory) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.userTestDataFactory = userTestDataFactory;
    }

    @Test
    public void testLoginSuccess() throws Exception {
        var userView = userTestDataFactory.createUser(String.format("test.user.%d@nix.io", currentTimeMillis()), "Test User", password);

        var request = new AuthRequest();
        request.setUsername(userView.getUsername());
        request.setPassword(password);

        MvcResult createResult = this.mockMvc
                .perform(post("/api/v1/public/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.AUTHORIZATION))
                .andReturn();

        var authUserView = fromJson(objectMapper, createResult.getResponse().getContentAsString(), UserView.class);
        assertEquals(userView.getId(), authUserView.getId(), "User ids must match!");
    }

    @Test
    public void testLoginFail() throws Exception {
        var userView = userTestDataFactory.createUser(String.format("test.user.%d@nix.io", currentTimeMillis()), "Test User", password);

        var request = new AuthRequest();
        request.setUsername(userView.getUsername());
        request.setPassword("zxc");

        this.mockMvc
                .perform(post("/api/public/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, request)))
                .andExpect(status().isUnauthorized())
                .andExpect(header().doesNotExist(HttpHeaders.AUTHORIZATION))
                .andReturn();
    }

    @Test
    public void testRegisterSuccess() throws Exception {
        var goodRequest = new CreateUserRequest();
        goodRequest.setUsername(String.format("test.user.%d@nix.com", currentTimeMillis()));
        goodRequest.setFullname("Test User A");
        goodRequest.setPassword(password);
        goodRequest.setRePassword(password);

        var createResult = this.mockMvc
                .perform(post("/api/public/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, goodRequest)))
                .andExpect(status().isOk())
                .andReturn();

        var userView = fromJson(objectMapper, createResult.getResponse().getContentAsString(), UserView.class);
        assertNotNull(userView.getId(), "User id must not be null!");
        assertEquals(goodRequest.getFullname(), userView.getFullname(), "User fullname  update isn't applied!");
    }

    @Test
    public void testRegisterFail() throws Exception {
        var badRequest = new CreateUserRequest();
        badRequest.setUsername("invalid.username");

        this.mockMvc
                .perform(post("/api/public/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(objectMapper, badRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Method argument validation failed")));
    }
}
