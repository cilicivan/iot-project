package hr.fer.tel.iot.project.controller;


import hr.fer.tel.iot.project.entity.Measurement;
import hr.fer.tel.iot.project.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/measurements")
public class MeasurementController {

    @Autowired
    private MeasurementRepository measurementRepository;

    @GetMapping
    public List<Measurement> allMeasurements(){
        return measurementRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Measurement getMeasurement(@PathVariable Long id){
        return measurementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Measurement does not exist."));
    }
}
