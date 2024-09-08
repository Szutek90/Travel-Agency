package com.app.api.router;

import com.app.api.dto.ResponseDto;
import com.app.service.TourWithCountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spark.ResponseTransformer;

import java.math.BigDecimal;
import java.time.LocalDate;

import static spark.Spark.*;

@Component
@RequiredArgsConstructor
public class TourWithCountryRouter {
    private final ResponseTransformer responseTransformer;
    private final TourWithCountryService service;

    public void routes() {
        path("/tour", () -> {
            path("/id", () -> get("/:id", (request, response) -> {
                        var id = Integer.parseInt(request.params(":id"));
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return new ResponseDto<>(service.getById(id));
                    },
                    responseTransformer));
            path("/country", () -> get("/:country", (request, response) -> {
                        var country = request.params(":country");
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return new ResponseDto<>(service.getByCountry(country));
                    },
                    responseTransformer));
            get("/price-range", (request, response) -> {
                        var from = new BigDecimal(request.queryParams("from"));
                        var to = new BigDecimal(request.queryParams("to"));
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return new ResponseDto<>(service.getToursInPriceRange(from, to));
                    },
                    responseTransformer);
            get("/price-to", (request, response) -> {
                        var to = new BigDecimal(request.queryParams("to"));
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return new ResponseDto<>(service.getToursCheaperThan( to));
                    },
                    responseTransformer);
            get("/price-from", (request, response) -> {
                        var from = new BigDecimal(request.queryParams("from"));
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return new ResponseDto<>(service.getToursMoreExpensiveThan( from));
                    },
                    responseTransformer);
            path("/date", () -> {
                get("", (request, response) -> {
                            var from = LocalDate.parse(request.queryParams("from"));
                            var to = LocalDate.parse(request.queryParams("to"));
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return new ResponseDto<>(service.getToursInRange(from, to));
                        },
                        responseTransformer);
                get("/after", (request, response) -> {
                            var from = LocalDate.parse(request.queryParams("from"));
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return new ResponseDto<>(service.getToursAfterDate(from));
                        },
                        responseTransformer);
                get("/before", (request, response) -> {
                            var to = LocalDate.parse(request.queryParams("to"));
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return new ResponseDto<>(service.getToursBeforeDate(to));
                        },
                        responseTransformer);
            });
        });
    }
}
