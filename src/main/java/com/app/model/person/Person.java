package com.app.model.person;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Person {
    protected int id;
    private String name;
    private String surname;
    private String email;
}
