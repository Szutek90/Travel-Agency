package com.app.service;

import com.app.dto.PersonDto;
import com.app.model.Person;
import com.app.repository.PersonRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public Person addPerson(PersonDto personDto) {
        if (personRepository.findByEmail(personDto.email()).isPresent()) {
            throw new IllegalArgumentException("Person with given email already exist");
        }
        return personRepository.save(Person.builder()
                .name(personDto.name())
                .surname(personDto.surname())
                .email(personDto.email())
                .build());
    }
}
