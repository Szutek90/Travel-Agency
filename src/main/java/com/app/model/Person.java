package com.app.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Person {
    final int id;
    final String name;
    final String surname;
    final String email;
}
