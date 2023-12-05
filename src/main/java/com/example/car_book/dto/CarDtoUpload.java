package com.example.car_book.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

@Data
public class CarDtoUpload {

    @NotBlank
    private String name;

    @NotBlank
    private String brand;

    private Boolean isAvailable;

    @NotNull
    @DecimalMin("0.01")
    @Digits(integer = 5, fraction = 2)
    private Double pricePerMin;

    @NotNull
    @DecimalMin("0.01")
    @Digits(integer = 5, fraction = 2)
    private Double pricePerHour;

    @NotNull
    @DecimalMin("0.01")
    @Digits(integer = 5, fraction = 2)
    private Double pricePerDay;

    @NotBlank
    private String number;

    @Min(value = 0)
    private Integer mileage;

    @NotBlank
    private String transmission;

    @Min(value = 2)
    private Integer seats;

    @Min(value = 2)
    private Integer luggage;

    @NotBlank
    private String fuel;

    @NotBlank
    private String description;

    private Double latitude;
    private Double longitude;

    @NotNull(message = "You must choose an image!")
    private MultipartFile file;

}