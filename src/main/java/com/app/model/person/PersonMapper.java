package com.app.model.person;

import java.util.function.ToIntFunction;

public interface PersonMapper {
    static void updateEmail(Person person, String email) {
        try {
            var field = Person.class.getDeclaredField("email");
            field.setAccessible(true);
            field.set(person, email);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    ToIntFunction<Person> toId = p -> p.id;
}
