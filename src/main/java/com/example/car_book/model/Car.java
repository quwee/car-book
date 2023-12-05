package com.example.car_book.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "car")
@Data
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "number")
    private String number;

    @Column(name = "price_per_min")
    private Double pricePerMin;

    @Column(name = "price_per_hour")
    private Double pricePerHour;

    @Column(name = "price_per_day")
    private Double pricePerDay;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "img_path")
    private String imgPath;

    @Column(name = "img_request_url")
    private String imgRequestUrl;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "transmission")
    private String transmission;

    @Column(name = "seats")
    private Integer seats;

    @Column(name = "luggage")
    private Integer luggage;

    @Column(name = "fuel")
    private String fuel;

    @Column(name = "description")
    private String description;

    @OneToOne(mappedBy = "car")
    private Ride order;

}
