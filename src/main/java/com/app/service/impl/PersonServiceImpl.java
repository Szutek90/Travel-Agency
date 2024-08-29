package com.app.service.impl;

import com.app.dto.person.CreatePersonDto;
import com.app.dto.person.GetPersonDto;
import com.app.dto.person.UpdatePersonDto;
import com.app.model.person.Person;
import com.app.repository.PersonRepository;
import com.app.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public GetPersonDto updatePerson(UpdatePersonDto updatePersonDto, Integer id) {
        if (personRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Person with given id does not exist");
        }
        var updatedPerson = personRepository.update(new Person(updatePersonDto), id);
        return updatedPerson.toGetPersonDto();
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

    @Override
    public List<GetPersonDto> getAllPersons() {
        return personRepository.findAll()
                .stream()
                .map(Person::toGetPersonDto)
                .toList();
    }
}
