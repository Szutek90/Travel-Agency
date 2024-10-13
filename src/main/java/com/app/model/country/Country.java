package com.app.model.country;

import com.app.dto.country.GetCountryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Country {
    protected final Integer id;
    protected final String name;

    public GetCountryDto toGetCountryDto() {
        return new GetCountryDto(name);
    }
}
