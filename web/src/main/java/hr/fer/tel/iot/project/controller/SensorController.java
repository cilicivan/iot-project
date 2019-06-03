package hr.fer.tel.iot.project.controller;

import hr.fer.tel.iot.project.entity.Sensor;
import hr.fer.tel.iot.project.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/sensors")
public class SensorController {

    @Autowired
    private SensorRepository sensorRepository;

    @GetMapping
    public ResponseEntity<List<Sensor>> allSensors(){
        return new ResponseEntity<>(sensorRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Sensor> getMeasurement(@PathVariable Long id){
        return new ResponseEntity<>(sensorRepository.getOne(id),HttpStatus.OK);
    }

}
