package com.example.car_book.service;

import com.example.car_book.model.Car;
import com.example.car_book.model.Ride;
import com.example.car_book.model.User;
import com.example.car_book.repository.CarRepository;
import com.example.car_book.repository.RideRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RideService {

    private final RideRepository rideRepository;
    private final CarRepository carRepository;


    public RideService(RideRepository rideRepository, CarRepository carRepository) {
        this.rideRepository = rideRepository;
        this.carRepository = carRepository;
    }

    public Optional<Ride> findRideByUserId(Long userId) {
        return rideRepository.findByUserId(userId);
    }

    @Transactional
    public void createRide(int carId) {
        Optional<Car> carOptional = carRepository.findById(carId);
        if(carOptional.isEmpty()) {
            return;
        }
        Optional<User> userOptional = User.getCurrentUser();
        if(userOptional.isEmpty()) {
            return;
        }

        Car car = carOptional.get();
        User user = userOptional.get();

        Ride ride = new Ride();
        ride.setCar(car);
        ride.setUser(user);
        ride.setCreatedAt(LocalDateTime.now());
        ride.setTotal(0.0);

        Ride savedRide = rideRepository.save(ride);

        car.setIsAvailable(false);

        carRepository.save(car);

    }

    @Transactional
    public void removeRide(Long userId) {
        Ride ride = findRideByUserId(userId).orElseThrow();
        rideRepository.delete(ride);
        Car car = carRepository.findById(ride.getCar().getId()).orElseThrow();
        car.setIsAvailable(true);
        carRepository.save(car);
    }
}
