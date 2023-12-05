package com.example.car_book.repository;

import com.example.car_book.model.Car;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    int countByIsAvailableTrue();
    List<Car> findByIsAvailableTrue();
    List<Car> findByIsAvailableTrue(Pageable pageable);
    Optional<Car> findByIdAndIsAvailableTrue(int id);
}
