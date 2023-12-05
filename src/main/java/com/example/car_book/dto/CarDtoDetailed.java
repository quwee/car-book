package com.example.car_book.dto;

import lombok.Data;

@Data
public class CarDtoDetailed {

    private int id;
    private String name;
    private String brand;
    private String number;
    private Double pricePerMin;
    private Double pricePerHour;
    private Double pricePerDay;
    private Double latitude;
    private Double longitude;
    private String imgRequestUrl;
    private Integer mileage;
    private String transmission;
    private Integer seats;
    private Integer luggage;
    private String fuel;
    private String description;

}
