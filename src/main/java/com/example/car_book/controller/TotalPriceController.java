package com.example.car_book.controller;

import com.example.car_book.dto.TotalPriceParameters;
import com.example.car_book.service.TotalPriceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TotalPriceController {

    private final TotalPriceCalculator totalPriceCalculator;

    @Autowired
    public TotalPriceController(TotalPriceCalculator totalPriceCalculator) {
        this.totalPriceCalculator = totalPriceCalculator;
    }

    @GetMapping("/check")
    public ResponseEntity<Double> checkTotalPrice(
            @ModelAttribute("totalPriceParameters") @Valid TotalPriceParameters totalPriceParameters,
            BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            System.out.println(totalPriceParameters);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0.0);
        }

        return ResponseEntity.ok(totalPriceCalculator.calculate(totalPriceParameters));
    }

}
