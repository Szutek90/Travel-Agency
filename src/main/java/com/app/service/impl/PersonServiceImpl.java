package com.app.service.impl;

import com.app.dto.person.CreatePersonDto;
import com.app.dto.person.GetPersonDto;
import com.app.model.person.Person;
import com.app.repository.PersonRepository;
import com.app.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    public GetPersonDto addPerson(CreatePersonDto personDto) {
        if (personRepository.findByEmail(personDto.email()).isPresent()) {
            throw new IllegalArgumentException("Person with given email already exist");
        }
        var personTosave = personRepository.save(Person.builder()
                .name(personDto.name())
                .surname(personDto.surname())
                .email(personDto.email())
                .build());
        return personTosave.toGetPersonDto();
    }

    @Override
    public void updateEmail(Person person, String email) {
        try {
            var field = Person.class.getDeclaredField("email");
            field.setAccessible(true);
            field.set(person, email);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public GetPersonDto getPersonById(int id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There is no Person with given id"))
                .toGetPersonDto();
    }

    @Override
    public GetPersonDto getPersonByNameAndSurname(String name, String surname) {
        return personRepository.findByNameAndSurname(name, surname)
                .orElseThrow(() -> new IllegalArgumentException("There is no Person with given id"))
                .toGetPersonDto();
    }
}
