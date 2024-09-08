package com.app.api.router;

import com.app.api.dto.ResponseDto;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import spark.ResponseTransformer;

import static spark.Spark.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultRouter {
    private final ResponseTransformer responseTransformer;
    private final Gson gson;

    public void routes(){
        path("", () -> {
            exception(RuntimeException.class, (ex, request, response) -> {
                var exceptionMessage = ex.getMessage();
                log.error("EX: {}", exceptionMessage);
                response.redirect("/error?msg=" + exceptionMessage, 301);
            });

            path("/error", () ->
                    get(
                            "",
                            (request, response) -> {
                                response.header("Content-Type", "application/json;charset=utf-8");
                                response.status(500);
                                var message = request.queryParams("msg");
                                return new ResponseDto<>(message);
                            },
                            responseTransformer
                    )
            );
            internalServerError((request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                return gson.toJson(new ResponseDto<>("Internal Server Error [PSZ]"));
            });

            notFound((request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                return gson.toJson(new ResponseDto<>("Not found [PSZ]"));
            });
        });
    }
}
