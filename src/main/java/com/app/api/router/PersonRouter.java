package com.app.api.router;

import com.app.api.dto.ResponseDto;
import com.app.dto.person.CreatePersonDto;
import com.app.dto.person.UpdatePersonDto;
import com.app.service.PersonService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spark.ResponseTransformer;

import static spark.Spark.*;

@Component
@RequiredArgsConstructor
public class PersonRouter {
    private final ResponseTransformer responseTransformer;
    private final PersonService personService;
    private final Gson gson;

    public void routes() {
        path("/person", () -> {
            post("", (request, response) -> {
                        var personToAdd = gson.fromJson(request.body(), CreatePersonDto.class);
                        response.header("Content-Type", "application/json;charset=utf-8");
                        response.status(201);
                        return new ResponseDto<>(personService.addPerson(personToAdd));
                    },
                    responseTransformer);
            get("", (request, response) -> {
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return new ResponseDto<>(personService.getAllPersons());
                    },
                    responseTransformer);
            get("/:id", (request, response) -> {
                        var id = Integer.parseInt(request.params(":id"));
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return new ResponseDto<>(personService.getPersonById(id));
                    },
                    responseTransformer);
            get("", (request, response) -> {
                        var name = request.queryParams("name");
                        var surname = request.queryParams("surname");
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return new ResponseDto<>(personService.getPersonByNameAndSurname(name, surname));
                    },
                    responseTransformer);
            put(
                    "/:id",
                    (request, response) -> {
                        var id = Integer.parseInt(request.params("id"));
                        var updatePersonDto
                                = gson.fromJson(request.body(), UpdatePersonDto.class);
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return new ResponseDto<>(personService.updatePerson(updatePersonDto, id));
                    },
                    responseTransformer
            );
        });
    }
}
