package com.library.nnamdi_azikiwe.controller;

import com.library.nnamdi_azikiwe.model.entity.Role;
import com.library.nnamdi_azikiwe.model.entity.User;
import com.library.nnamdi_azikiwe.model.repository.RoleRepository;
import com.library.nnamdi_azikiwe.model.repository.UserRepository;
import com.library.nnamdi_azikiwe.model.requests.AuthenticationRequest;
import com.library.nnamdi_azikiwe.model.response.SigninResponse;
import com.library.nnamdi_azikiwe.model.response.SignupResponse;
import com.library.nnamdi_azikiwe.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;


    @PostMapping( "/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody AuthenticationRequest signUpRequest) {
        SignupResponse signupResponse = new SignupResponse();
        AuthenticationRequest authenticationRequest =
                new AuthenticationRequest(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));
        signupResponse.setData(authenticationRequest);


        // Check that there's no user with the new username
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            signupResponse.setMessage("Error: Username is already taken");
            return new ResponseEntity<>(signupResponse, HttpStatus.BAD_REQUEST);
        }

        signupResponse.setMessage("Successful: User created");
        User user = new User(authenticationRequest);

        // Role will be used in the future to implement admin and user authentication.
        // ATM it's being hardcoded
        Optional<Role> roleExists = roleRepository.findByName("user");
        if (roleExists.isPresent()) {
            user.setRole(roleExists.get());
        } else {
            Role role = roleRepository.save(new Role("user"));
            user.setRole(role);
        }
        userRepository.save(user);
        return new ResponseEntity<>(signupResponse, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<SigninResponse> signin(@Valid @RequestBody AuthenticationRequest loginRequest) {
        SigninResponse response = new SigninResponse();
        String jwt = "";
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            jwt = jwtUtils.generateJwtToken(authentication);
            response.setMessage("Successful sign in");
            response.setJwt(jwt);
        } catch (Exception e) {
            response.setMessage("Unsuccessful sign in. Error: " + e);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
