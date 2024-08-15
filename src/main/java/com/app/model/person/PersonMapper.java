package com.app.model.person;

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

    ;
}
