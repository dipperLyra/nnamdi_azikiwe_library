package com.library.nnamdi_azikiwe.model.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class BookUpdateRequest {
    private int id;
    private String title;
    private String author;
    private String callNumber;
}
