package com.app.model.person;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@Data
@EqualsAndHashCode
@ToString
public class Person {
    protected Integer id;
    private String name;
    private String surname;
    private String email;
}
