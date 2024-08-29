package com.app.service;

import com.app.dto.person.CreatePersonDto;
import com.app.dto.person.GetPersonDto;
import com.app.model.person.Person;

public interface PersonService {
    GetPersonDto addPerson(CreatePersonDto personDto);
    void updateEmail(Person person, String email);
    GetPersonDto getPersonById(int id);
    GetPersonDto getPersonByNameAndSurname(String name, String surname);
}
