package com.library.nnamdi_azikiwe.controller;

import com.library.nnamdi_azikiwe.model.entity.Role;
import com.library.nnamdi_azikiwe.model.entity.User;
import com.library.nnamdi_azikiwe.model.repository.RoleRepository;
import com.library.nnamdi_azikiwe.model.repository.UserRepository;
import com.library.nnamdi_azikiwe.model.requests.AuthenticationRequest;
import com.library.nnamdi_azikiwe.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
    public String signup(@RequestBody AuthenticationRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername()))
            return "Error: Username is already taken!";

        User user = new User(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()));

        Role role = roleRepository.findByName("user")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRole(role);
        userRepository.save(user);

        return  "Successful, user created!";
    }

    @PostMapping("/authenticate")
    public String signin(@Valid @RequestBody AuthenticationRequest loginRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        return "Authentication successful. Token: " + jwt;
    }

    @PostMapping("/role")
    public Role role(@RequestBody Role role) {
        return roleRepository.save(role);
    }
}
