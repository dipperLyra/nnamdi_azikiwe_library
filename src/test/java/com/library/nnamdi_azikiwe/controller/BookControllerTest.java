package com.library.nnamdi_azikiwe.controller;

import com.library.nnamdi_azikiwe.model.entity.Book;
import com.library.nnamdi_azikiwe.model.repository.UserRepository;
import com.library.nnamdi_azikiwe.security.AuthEntryPointJwt;
import com.library.nnamdi_azikiwe.security.UserDetailsServiceImpl;
import com.library.nnamdi_azikiwe.service.book.BookService;
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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = {BookController.class, UserDetailsServiceImpl.class})
class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;
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
    @Autowired
    AuthenticationManager authenticationManager;

    Book mockBook;
    String bookJson;
    String jwt;

    @BeforeEach
    void setUp() {
        this.mockBook = new Book("Oathbringer", "Brendan Sanderson", "DS559.46 .H35 2017");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("urusi", "password"));
        System.out.println("e reach here?");
        this.jwt = jwtUtils.generateJwtToken(authentication);
        System.out.println("e reach here?");
    }


/*    @Test
    void add() throws Exception {
        Mockito.when(bookService.add(Mockito.any(Book.class))).thenReturn(mockBook);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/book")
                .header("Authorization", this.jwt)
                .accept(MediaType.APPLICATION_JSON).content(bookJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }*/


}