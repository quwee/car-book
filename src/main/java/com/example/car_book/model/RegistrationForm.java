package com.example.car_book.model;

import lombok.Data;

@Data
public class RegistrationForm {

    private String username;
    private String password;
    private String email;
    private String ccNumber;
    private String ccExpiration;
    private String ccCvv;

}
