package com.app.model.country;

import com.app.dto.country.GetCountryDto;
import lombok.*;

//TODO [1] Aby móc mapować na obiekt tego typu ( maptoBean ) muszą być gettery, np. przez adnotację Data
// przez to leży Enkapsulacja. Czy tak ma być?
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
