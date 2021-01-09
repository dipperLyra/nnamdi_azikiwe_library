package com.library.nnamdi_azikiwe.model.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BorrowBookRequest {
    private String callNumber;
    private String title;
    private String username;
}
