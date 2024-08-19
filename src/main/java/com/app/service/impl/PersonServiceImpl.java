package com.app.service.impl;

import com.app.dto.PersonDto;
import com.app.model.person.Person;
import com.app.model.person.PersonMapper;
import com.app.repository.PersonRepository;
import com.app.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
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

    @Override
    public void updateEmail(Person person, String email) {
        PersonMapper.updateEmail(person, email);
    }

    @Override
    public Person getPersonById(int id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There is no Person with given id"));
    }

    @Override
    public Person getPersonByNameAndSurname(String name, String surname) {
        return personRepository.findByNameAndSurname(name, surname)
                .orElseThrow(() -> new IllegalArgumentException("There is no Person with given id"));
    }
}
