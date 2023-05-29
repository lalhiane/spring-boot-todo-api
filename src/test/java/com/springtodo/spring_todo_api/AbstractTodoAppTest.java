package com.springtodo.spring_todo_api;

import org.junit.Before;

import org.junit.Ignore;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import org.springframework.http.MediaType;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;

import org.springframework.test.web.servlet.ResultActions;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.jayway.jsonpath.JsonPath;

import com.springtodo.spring_todo_api.security.auth.AuthenticationRequest;

import com.springtodo.spring_todo_api.security.user.User;

import static org.mockito.BDDMockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // tests for http requests
@AutoConfigureMockMvc // similation => htttp requests
public abstract class AbstractTodoAppTest {

    private final String USERNAME_FOR_TEST = "lalhiane@gmail.com";

    private final String PASSWORD_FOR_TEST = "lalhiane1234";

    private final String AUTH_HEADER = "Authorization";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private UserDetailsService userDetailsService;

    @Before
    public void setup() {

        final User user = new User(1, "Lahcen", "Alhiane", USERNAME_FOR_TEST,
                new BCryptPasswordEncoder().encode(PASSWORD_FOR_TEST));

        given(userDetailsService.loadUserByUsername(user.getUsername())).willReturn(user);

    }

    public ResultActions login(String email, String password) throws Exception {

        AuthenticationRequest authRequest = new AuthenticationRequest(email, password);

        return mockMvc.perform(

                post("/api/v1/auth/authentication")

                        .contentType(MediaType.APPLICATION_JSON)

                        .content(new ObjectMapper().writeValueAsString(authRequest))

        );

    }

    public MockHttpServletRequestBuilder doGetRequest(String url) {

        return get(url).header(AUTH_HEADER, getHeader());

    }

    public MockHttpServletRequestBuilder doPostRequest(String url) {

        return get(url).header(AUTH_HEADER, getHeader());

    }

    public String getHeader() {

        try {

            MvcResult result = login(USERNAME_FOR_TEST, PASSWORD_FOR_TEST).andReturn();

            String token = JsonPath.read(result.getResponse().getContentAsString(), "$.token");

            String header = String.format("Bearer %s", token);

            return header;

        } catch (Exception ex) {

            return null;

        }

    }

}
