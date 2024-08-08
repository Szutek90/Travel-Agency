package com.app.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Person {
    private int id;
    private String name;
    private String surname;
    private String email;
}
