package com.example.car_book.dto;

import lombok.Data;

@Data
public class CarDtoSimple {

    private int id;
    private String name;
    private String brand;
    //private String imgPath;
    private String imgRequestUrl;
    private Double pricePerMin;
    private Double pricePerHour;
    private Double pricePerDay;
    /*private Double latitude;
    private Double longitude;*/

}
