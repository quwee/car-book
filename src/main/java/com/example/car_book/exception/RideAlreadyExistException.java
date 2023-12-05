package com.example.car_book.exception;

public class RideAlreadyExistException extends RuntimeException {
    public RideAlreadyExistException(String message) {
        super(message);
    }
}
