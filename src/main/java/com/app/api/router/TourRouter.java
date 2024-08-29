package com.app.api.router;

import com.app.api.dto.ResponseDto;
import com.app.dto.country.CreateCountryDto;
import com.app.service.CountryService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spark.ResponseTransformer;

import static spark.Spark.*;

@Component
@RequiredArgsConstructor
public class TourRouter {
    private final ResponseTransformer responseTransformer;
    private final CountryService countryService;
    private final Gson gson;

    public void routes() {
        path("/country", () -> {
            get("", (request, response) -> {
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return new ResponseDto<>(countryService.getAllCountries());
                    },
                    responseTransformer);

            post("", (request, response) -> {
                        var createdCountry = gson
                                .fromJson(request.body(), CreateCountryDto.class);
                        response.header("Content-Type", "application/json;charset=utf-8");
                        response.status(201);
                        return new ResponseDto<>(countryService.addCountry(createdCountry));
                    },
                    responseTransformer);
        });
    }
}
