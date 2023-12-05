package com.example.car_book.controller;

import com.example.car_book.dto.CarDtoUpload;
import com.example.car_book.model.Car;
import com.example.car_book.exception.CarNotFoundException;
import com.example.car_book.dto.ResponseCarObject;
import com.example.car_book.service.CarService;
import com.example.car_book.util.CarValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class CarController {

    private final CarService carService;
    private final CarValidator carValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public CarController(CarService carService, ModelMapper modelMapper, CarValidator carValidator) {
        this.carService = carService;
        this.carValidator = carValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add-car")
    public String getAddCarForm(@ModelAttribute("car") CarDtoUpload car) {
        return "addCarFrom";
    }

    @PostMapping("/add-car")
    public String addCar(@ModelAttribute("car") @Valid CarDtoUpload carDtoUpload, BindingResult bindingResult) {
        carValidator.validate(carDtoUpload, bindingResult);
        if(bindingResult.hasErrors())
            return "addCarFrom";

        carService.addCar(carDtoUpload);
        return "redirect:/cars";
    }

    @ResponseBody
    @GetMapping("/cars-json")
    public ResponseCarObject getAvailableCars(@RequestParam(name = "pageNum") int pageNum,
                                     @RequestParam(name = "pageSize") int pageSize) {
        return new ResponseCarObject(
                carService.countPagesAvailable(),
                carService.findAvailableCarsAsDtoSimple(pageNum, pageSize));
    }

    /* Used in confirm_start_ride.js */
    @ResponseBody
    @GetMapping("/cars-json/{id}")
    public Car getAvailableCarById(@PathVariable("id") int carId) {
        return carService.findAvailableById(carId)
                .orElseThrow(() -> new CarNotFoundException("Car with that id: " + carId + " not found."));
    }


    @GetMapping("/access-denied")
    public String accessDeniedHandler() {
        return "403";
    }

}
