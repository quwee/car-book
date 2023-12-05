package com.example.car_book.dto;

import com.example.car_book.dto.CarDtoSimple;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCarObject {
    Integer countPages;
    List<CarDtoSimple> cars;
}
