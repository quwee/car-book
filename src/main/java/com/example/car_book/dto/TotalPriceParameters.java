package com.example.car_book.dto;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class TotalPriceParameters {

    @Min(value = 0, message = "Must be grater than 0")
    @Max(value = 59, message = "Must be less than 59")
    private Integer mins;

    @Min(value = 0, message = "Must be grater than 0")
    @Max(value = 23, message = "Must be less than 23")
    private Integer hours;

    @Min(value = 0, message = "Must be grater than 0")
    @Max(value = 30, message = "Must be less than 30")
    private Integer days;

}
