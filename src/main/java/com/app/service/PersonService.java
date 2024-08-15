package com.app.service;

import com.app.dto.PersonDto;
import com.app.model.person.Person;

public interface PersonService {
    Person addPerson(PersonDto personDto);
    void updateEmail(Person person, String email);
    Person getPersonById(int id);
    Person getPersonByNameAndSurname(String name, String surname);
}
