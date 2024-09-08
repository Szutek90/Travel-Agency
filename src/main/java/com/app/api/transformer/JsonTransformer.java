package com.app.api.transformer;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import spark.ResponseTransformer;

@Configuration
@RequiredArgsConstructor
public class JsonTransformer implements ResponseTransformer {
    private final Gson gson;

    @Override
    public String render(Object o) {
        return gson.toJson(o);
    }
}
