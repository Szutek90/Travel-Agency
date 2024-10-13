package com.app.model.country;

import com.app.dto.country.GetCountryDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Data
public class Country {
    protected Integer id;
    protected String name;

    public GetCountryDto toGetCountryDto() {
        return new GetCountryDto(name);
    }
}
