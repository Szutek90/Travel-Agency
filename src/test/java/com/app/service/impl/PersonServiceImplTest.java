package com.app.service.impl;

import com.app.dto.person.CreatePersonDto;
import com.app.dto.person.UpdatePersonDto;
import com.app.model.person.Person;
import com.app.repository.PersonRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PersonServiceImplTest {
    static Person person;
    static CreatePersonDto createPersonDto;

    @Mock
    PersonRepository repository;

    @InjectMocks
    PersonServiceImpl service;

    @BeforeAll
    static void beforeAll() {
        person = Person.builder()
                .id(1)
                .email("kowal@gmail.com")
                .name("jan")
                .surname("kowalski")
                .build();

        createPersonDto = new CreatePersonDto("jan", "kowalski", "kowal@gmail.com");
    }

    @Test
    @DisplayName("When adding new Person")
    void test1() {
        when(repository.findByEmail(any())).thenReturn(Optional.empty());
        when(repository.save(any(Person.class))).thenReturn(person);
        assertThat(service.addPerson(createPersonDto)).isEqualTo(person.toGetPersonDto());
    }

    @Test
    @DisplayName("When adding new Person with existing email")
    void test2() {
        when(repository.findByEmail(any())).thenReturn(Optional.of(person));
        assertThatThrownBy(() -> service.addPerson(createPersonDto))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("When getting Person by id")
    void test3() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(person));
        assertThat(service.getPersonById(8)).isEqualTo(person.toGetPersonDto());
    }

    @Test
    @DisplayName("When there is no Person with given id")
    void test4() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getPersonById(8))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("When there is no Person with given name and surname")
    void test5() {
        when(repository.findByNameAndSurname(anyString(), anyString()))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getPersonByNameAndSurname("jan", "kowalski"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("When getting Person with name and surname")
    void test6() {
        when(repository.findByNameAndSurname(anyString(), anyString()))
                .thenReturn(Optional.of(person));
        assertThat(service.getPersonByNameAndSurname("jan", "kowalski"))
                .isEqualTo(person.toGetPersonDto());
    }

    @Test
    @DisplayName("When getting all persons")
    void test7() {
        when(repository.findAll()).thenReturn(List.of(person));
        assertThat(service.getAllPersons()).hasSize(1)
                .isEqualTo(List.of(person.toGetPersonDto()));
    }

    @Test
    @DisplayName("When updating person")
    void test8() {
        var updatePersonDto = new UpdatePersonDto("jan", "updated", "kowal@gmail.com");
        var updatedPerson = Person.builder()
                .id(1)
                .email("kowal@gmail.com")
                .name("jan")
                .surname("updated")
                .build();
        when(repository.findById(anyInt())).thenReturn(Optional.of(person));
        when(repository.update(any(Person.class), any())).thenReturn(updatedPerson);
        assertThat(service.updatePerson(updatePersonDto, 1)).isEqualTo(updatedPerson.toGetPersonDto());
    }

    @Test
    @DisplayName("When updating person there is no person with given id")
    void test9() {
        var updatePersonDto = new UpdatePersonDto("jan", "updated", "kowal@gmail.com");
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.updatePerson(updatePersonDto, 1))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("Person with given id does not exist");
    }
}
