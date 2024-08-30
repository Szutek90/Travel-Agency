package com.app.api.router;

import com.app.api.dto.ResponseDto;
import com.app.dto.country.CreateCountryDto;
import com.app.dto.travel_agency.CreateTravelAgencyDto;
import com.app.service.TravelAgencyService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spark.ResponseTransformer;

import static spark.Spark.*;

@Component
@RequiredArgsConstructor
public class TravelAgencyRouter {
    private final ResponseTransformer responseTransformer;
    private final TravelAgencyService service;
    private final Gson gson;

    public void routes() {
        path("/travel-agency", () -> {
            path("/id", () -> {
                get("/:id", (request, response) -> {
                            var id = Integer.parseInt(request.params(":id"));
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return new ResponseDto<>(service.getTravelAgencyById(id));
                        },
                        responseTransformer);
            });
            path("/name", () -> {
                get("/:name", (request, response) -> {
                            var name = request.params(":name");
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return new ResponseDto<>(service.getTravelAgencyByName(name));
                        },
                        responseTransformer);
            });
            path("/city", () -> {
                get("/:city", (request, response) -> {
                            var city = request.params(":city");
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return new ResponseDto<>(service.getAllTravelAgenciesByCity(city));
                        },
                        responseTransformer);
            });
            post("", (request, response) -> {
                        var createdAgency = gson
                                .fromJson(request.body(), CreateTravelAgencyDto.class);
                        response.header("Content-Type", "application/json;charset=utf-8");
                        response.status(201);
                        return new ResponseDto<>(service.addTravelAgency(createdAgency));
                    },
                    responseTransformer);
            get("", (request, response) -> {
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return new ResponseDto<>(service.getAllTravelAgency());
                    },
                    responseTransformer);
        });
    }
}
