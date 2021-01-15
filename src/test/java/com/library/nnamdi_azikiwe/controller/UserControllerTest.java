package com.library.nnamdi_azikiwe.controller;

import com.library.nnamdi_azikiwe.model.repository.UserRepository;
import com.library.nnamdi_azikiwe.model.requests.AuthenticationRequest;
import com.library.nnamdi_azikiwe.model.response.SigninResponse;
import com.library.nnamdi_azikiwe.model.response.SignupResponse;
import com.library.nnamdi_azikiwe.security.AuthEntryPointJwt;
import com.library.nnamdi_azikiwe.security.UserDetailsServiceImpl;
import com.library.nnamdi_azikiwe.util.JwtUtils;
import com.library.nnamdi_azikiwe.util.TimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = {UserController.class, UserDetailsServiceImpl.class})
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserController userController;
    @MockBean
    UserRepository userRepository;
    @MockBean
    JwtUtils jwtUtils;
    @MockBean
    AuthEntryPointJwt authEntryPointJwt;
    @MockBean
    TimeUtils timeUtils;


    AuthenticationRequest authRequest;
    String authRequestJson;
    SignupResponse signupResponse;
    SigninResponse signinResponse;


    @BeforeEach
    void setUp() {
        this.authRequest = new AuthenticationRequest("urusi", "password");
        this.authRequestJson = " {\"username\": \"urusi\", \"password\": \"password\"} ";

        this.signupResponse = new SignupResponse();
        this.signinResponse = new SigninResponse();
    }

    @Test
    void signup() throws Exception {
        this.signupResponse.setData(authRequest);
        this.signupResponse.setMessage("Successful");

        Mockito.when(userController.signup(Mockito.any(AuthenticationRequest.class)))
                .thenReturn(new ResponseEntity<>(signupResponse, HttpStatus.CREATED));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/signup")
                .accept(MediaType.APPLICATION_JSON).content(authRequestJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //System.out.println(result.getResponse().getContentAsString());
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void signIn() throws Exception {
        Mockito.when(userController.signin(Mockito.any(AuthenticationRequest.class)))
                .thenReturn(new ResponseEntity<>(signinResponse, HttpStatus.OK));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/authenticate")
                .accept(MediaType.APPLICATION_JSON).content(authRequestJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}