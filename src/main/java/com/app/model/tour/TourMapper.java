package com.app.model.tour;

import java.util.function.ToIntFunction;

public interface TourMapper {
    ToIntFunction<Tour> toId = t -> t.id;
}
