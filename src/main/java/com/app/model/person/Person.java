package com.app.model.person;

import com.app.dto.person.GetPersonDto;
import com.app.dto.person.UpdatePersonDto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Person {
    protected Integer id;
    private String name;
    private String surname;
    private String email;

    public Person(UpdatePersonDto updatePersonDto) {
        name = updatePersonDto.name();
        surname = updatePersonDto.surname();
        email = updatePersonDto.email();
    }

    public GetPersonDto toGetPersonDto() {
        return new GetPersonDto(name, surname, email);
    }
}
