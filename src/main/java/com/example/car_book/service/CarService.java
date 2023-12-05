package com.example.car_book.service;

import com.example.car_book.dto.CarDtoSimple;
import com.example.car_book.dto.CarDtoUpload;
import com.example.car_book.model.Car;
import com.example.car_book.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CarService {

    @Value("${upload-dir-path}")
    private String uploadDirPath;
    @Value("${img-request-url}")
    private String imgRequestUrl;

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CarService(CarRepository carRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }

    public int countPagesAvailable() {
        int count = carRepository.countByIsAvailableTrue();
        int divisor = 3;
        int quotient = count / divisor;
        int remainder = count % divisor;
        if(remainder > 0)
            quotient++;

        System.out.println("countPages: " + quotient);

        return quotient;
    }

    public List<Car> findAvailable() {
        return carRepository.findByIsAvailableTrue();
    }

    public List<CarDtoSimple> findAvailableCarsAsDtoSimple(int pageNum, int pageSize) {
        return carListToCarDtoList(carRepository.findByIsAvailableTrue(PageRequest.of(pageNum, pageSize)));
    }

    public List<CarDtoSimple> findAvailableFeaturedCars() {
        return carListToCarDtoList(carRepository.findByIsAvailableTrue(PageRequest.of(0,4)));
    }

    public Optional<Car> findByIdd(int id) {
        return carRepository.findById(id);
    }

    public Optional<Car> findAvailableById(int id) {
        return carRepository.findByIdAndIsAvailableTrue(id);
    }

    @Transactional
    public void addCar(CarDtoUpload carDtoUpload) {
        Car car = modelMapper.map(carDtoUpload, Car.class);

        car.setIsAvailable(true);
        setRandomLocation(car);

        String imgName = System.currentTimeMillis() + getFileExtension(Objects.requireNonNull(carDtoUpload.getFile().getOriginalFilename()));

        String uploadImgPathStr = uploadDirPath + imgName;

        car.setImgPath(uploadImgPathStr);
        car.setImgRequestUrl(imgRequestUrl + imgName);

        Path uploadImgPath = Paths.get(uploadImgPathStr);

        try {
            carDtoUpload.getFile().transferTo(uploadImgPath);
            carRepository.save(car);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<CarDtoSimple> carListToCarDtoList(List<Car> carList) {
        List<CarDtoSimple> carDtoSimpleList = new ArrayList<>();
        for(Car car : carList)
            carDtoSimpleList.add(modelMapper.map(car, CarDtoSimple.class));
        return carDtoSimpleList;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        return fileName.substring(dotIndex);
    }

    private void setRandomLocation(Car car) {
        double minLatitude = 53.85;
        double maxLatitude = 53.94;
        double minLongitude = 27.47;
        double maxLongitude = 27.67;

        double randomLatitude = minLatitude + (maxLatitude - minLatitude) * Math.random();
        double randomLongitude = minLongitude + (maxLongitude - minLongitude) * Math.random();

        car.setLatitude(randomLatitude);
        car.setLongitude(randomLongitude);
    }
}
