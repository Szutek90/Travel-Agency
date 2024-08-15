package com.app.model.country;

import java.util.function.ToIntFunction;

public interface CountryMapper {
    ToIntFunction<Country> toId = country -> country.id;
}
