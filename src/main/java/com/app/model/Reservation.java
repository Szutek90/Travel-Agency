package com.app.model;

import lombok.Data;

import java.util.List;

@Data
public class Reservation {
    private int id;
    private int customerId;
    private int quantityOfPeople;
    private int discount;
    private List<Components> components;
}
