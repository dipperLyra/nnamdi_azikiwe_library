package com.library.nnamdi_azikiwe.model.entity;

import com.library.nnamdi_azikiwe.model.requests.AuthenticationRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;

    @NotBlank @Column(columnDefinition = "TEXT")
    private String password;

    @OneToOne
    private Role role;

    public User(AuthenticationRequest authenticationRequest) {
        this.username = authenticationRequest.getUsername();
        this.password = authenticationRequest.getPassword();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
