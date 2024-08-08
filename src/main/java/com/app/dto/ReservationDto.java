package com.app.dto;

import com.app.model.Components;

import java.util.List;

public record ReservationDto(int customerId, int quantityOfPeople, int discount, List<Components> components) {
}
