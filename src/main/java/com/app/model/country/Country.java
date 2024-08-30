package com.app.model.country;

import com.app.dto.country.GetCountryDto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Country {
    protected Integer id;
    protected String name;

    public GetCountryDto toGetCountryDto() {
        return new GetCountryDto(name);
    }
}
