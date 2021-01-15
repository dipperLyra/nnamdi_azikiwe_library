package com.library.nnamdi_azikiwe.model.response;

import com.library.nnamdi_azikiwe.model.requests.AuthenticationRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupResponse {
    private String message;
    private AuthenticationRequest data;
}
