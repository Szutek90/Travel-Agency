package com.app.service;

import com.app.dto.person.CreatePersonDto;
import com.app.dto.person.GetPersonDto;
import com.app.dto.person.UpdatePersonDto;

import java.util.List;

public interface PersonService {
    GetPersonDto addPerson(CreatePersonDto personDto);
    GetPersonDto updatePerson(UpdatePersonDto updatePersonDto, Integer id);
    GetPersonDto getPersonById(int id);
    GetPersonDto getPersonByNameAndSurname(String name, String surname);
    List<GetPersonDto> getAllPersons();
}
