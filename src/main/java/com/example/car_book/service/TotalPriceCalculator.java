package com.example.car_book.service;

import com.example.car_book.dto.TotalPriceParameters;
import com.example.car_book.model.Ride;
import com.example.car_book.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class TotalPriceCalculator {

    private final RideService rideService;

    @Autowired
    public TotalPriceCalculator(RideService rideService) {
        this.rideService = rideService;
    }

    public double calculate(TotalPriceParameters parameters) {

        if(parameters.getMins() == null)
            parameters.setMins(0);
        if(parameters.getHours() == null)
            parameters.setHours(0);
        if(parameters.getDays() == null)
            parameters.setDays(0);

        User user = User.getCurrentUser().orElseThrow();
        Ride ride = rideService.findRideByUserId(user.getId()).orElseThrow();

        LocalDateTime start = ride.getCreatedAt();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime finish =
                now.plusMinutes(parameters.getMins())
                        .plusHours(parameters.getHours())
                        .plusDays(parameters.getDays());

        Duration duration = Duration.between(start, finish);

        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;

        return days * ride.getCar().getPricePerDay() +
                hours * ride.getCar().getPricePerHour() +
                minutes * ride.getCar().getPricePerMin();
    }

    public double calculate() {
        User user = User.getCurrentUser().orElseThrow();
        Ride ride = rideService.findRideByUserId(user.getId()).orElseThrow();

        LocalDateTime start = ride.getCreatedAt();
        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(start, now);

        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;

        return days * ride.getCar().getPricePerDay() +
                hours * ride.getCar().getPricePerHour() +
                minutes * ride.getCar().getPricePerMin();
    }

}
