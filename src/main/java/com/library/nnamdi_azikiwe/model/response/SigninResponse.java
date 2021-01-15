package com.library.nnamdi_azikiwe.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SigninResponse {
    private String jwt;
    private String message;

}
