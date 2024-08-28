package com.app.model.person;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@EqualsAndHashCode
@ToString
public class Person {
    protected Integer id;
    private String name;
    private String surname;
    private String email;
}
