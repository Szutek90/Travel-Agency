package com.app.model.person;

import java.util.function.ToIntFunction;

public interface PersonMapper {
    ToIntFunction<Person> toId = p -> p.id;
}
