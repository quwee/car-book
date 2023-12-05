package com.example.car_book.controller;

import com.example.car_book.model.Ride;
import com.example.car_book.model.User;
import com.example.car_book.exception.RideAlreadyExistException;
import com.example.car_book.service.CarService;
import com.example.car_book.service.RideService;
import com.example.car_book.service.TotalPriceCalculator;
import com.example.car_book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class HomeController {

    private final CarService carService;
    private final UserService userService;
    private final RideService rideService;
    private final TotalPriceCalculator totalPriceCalculator;

    @Autowired
    public HomeController(CarService carService, UserService userService, RideService rideService, TotalPriceCalculator totalPriceCalculator) {
        this.carService = carService;
        this.userService = userService;
        this.rideService = rideService;
        this.totalPriceCalculator = totalPriceCalculator;
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("cars", carService.findAvailableFeaturedCars());
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/cars")
    public String getAvailableCars(Model model) {
        model.addAttribute("countPages", carService.countPagesAvailable());
        model.addAttribute("carsFirstPage", carService.findAvailableCarsAsDtoSimple(0, 3));
        return "car";
    }

    @GetMapping("/pricing")
    public String pricing(Model model) {
        model.addAttribute("cars", carService.findAvailable());
        return "pricing";
    }

    @GetMapping("/cars/{id}")
    public String getAvailableCarDetails(@PathVariable("id") int id, Model model) {
        model.addAttribute("car", carService.findAvailableById(id)
                .orElseThrow(() -> new NoSuchElementException("Car with that id: " + id + " not found!")));
        return "car-single";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String profileEdit() {
        return "profile_edit_form";
    }

    @PostMapping("/profile/edit")
    public String processProfileEdit(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "profile_edit_form";
        userService.update(user);
        return "redirect:/profile";
    }

    @GetMapping("/confirm-start-ride/{id}")
    public String confirmStartRide(@PathVariable("id") int carId, Model model) {
        if(User.isAuthenticated()) {
            if(rideService.findRideByUserId(User.getCurrentUser().orElse(null).getId()).isEmpty()) {
                model.addAttribute("car", carService.findAvailableById(carId)
                        .orElseThrow(() -> new NoSuchElementException("Car with that id: " + carId + " not found!")));
                return "confirm_start_ride";
            }
            else {
                throw new RideAlreadyExistException("Finish the current ride before starting a new one");
            }
        }
        else {
            return "redirect:/auth/login";
        }
    }

    @PostMapping("/create-ride/{id}")
    public String createRide(@PathVariable("id") int carId, Model model) {
        if(User.isAuthenticated()) {
            rideService.createRide(carId);

            return "redirect:/ride";
        }
        else {
            return "redirect:/auth/login";
        }
    }

    @PostMapping("/ride/finish-ride")
    public String finishRide() {
        rideService.removeRide(User.getCurrentUser().orElse(null).getId());
        return "redirect:/ride";
    }


    @GetMapping("/ride")
    public String ride(Model model) {
        Optional<Ride> rideOptional = rideService.findRideByUserId(User.getCurrentUser().orElse(null).getId());
        rideOptional
                .ifPresentOrElse(ride -> {
                    ride.setTotal(totalPriceCalculator.calculate());
                    model.addAttribute("isRideExist", true);
                    model.addAttribute("ride", ride);
                    model.addAttribute("start", ride.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
                    model.addAttribute("total", new DecimalFormat("#.##").format(ride.getTotal()));
                        },
                        () -> model.addAttribute("isRideExist", false));
        return "ride";
    }



    @ExceptionHandler(NoSuchElementException.class)
    private String carNotFoundExceptionHandler(NoSuchElementException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "404";
    }

    @ExceptionHandler(RideAlreadyExistException.class)
    private String rideAlreadyExistException(RideAlreadyExistException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error_ride";
    }
}
