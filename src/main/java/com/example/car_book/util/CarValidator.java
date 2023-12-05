package com.example.car_book.util;

import com.example.car_book.dto.CarDtoUpload;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CarValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(CarDtoUpload.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CarDtoUpload carDtoUpload = (CarDtoUpload) target;

        String fileName = carDtoUpload.getFile().getOriginalFilename();
        String fileExtension = "";
        if (fileName != null) {
            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                fileExtension = fileName.substring(dotIndex + 1);
                if (!fileExtension.equalsIgnoreCase("jpg") && !fileExtension.equalsIgnoreCase("png")
                        && !fileExtension.equalsIgnoreCase("webp"))
                    errors.rejectValue("file", "error.invalidExtension", "Only JPG, PNG and WEBP files are allowed.");
            } else {
                errors.rejectValue("file", "error.invalidExtension", "Only JPG, PNG and WEBP files are allowed.");
            }
        }
    }
}
