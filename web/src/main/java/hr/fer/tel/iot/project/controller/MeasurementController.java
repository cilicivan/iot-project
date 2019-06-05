package hr.fer.tel.iot.project.controller;


import hr.fer.tel.iot.project.entity.Measurement;
import hr.fer.tel.iot.project.entity.Sensor;
import hr.fer.tel.iot.project.repository.MeasurementRepository;
import hr.fer.tel.iot.project.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/measurements")
public class MeasurementController {

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @GetMapping
    public List<Measurement> allMeasurements() {
        return measurementRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Measurement getMeasurement(@PathVariable Long id) {
        return measurementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Measurement does not exist."));
    }

    @PostMapping
    public Measurement createMeasurement(@RequestParam(value = "parameter") String parameter,
                                         @RequestParam(value = "value" ) Double value,
                                         @RequestParam(value = "time") Long time,
                                         @RequestParam(value = "sensorSerial" ) String sensorSerial) {
        System.out.println("slfkmgsfdk");

        Sensor sensor = sensorRepository.findBySerial(sensorSerial);
        Measurement measurement = new Measurement(parameter,value,null,time, sensor);
        sensorRepository.save(sensor);

        measurementRepository.save(measurement);

        return measurement;
    }

    @PostMapping(value = "/presence")
    public Measurement createPresenceMEasurement(@RequestParam(value = "presence") Boolean presence,
                                                 @RequestParam(value = "time") Long time,
                                                 @RequestParam(value = "sensorSerial") String sensorSerial){
        Sensor sensor = sensorRepository.findBySerial(sensorSerial);
        Measurement measurement = new Measurement(null,null,presence,time, sensor);
        measurementRepository.save(measurement);
        sensorRepository.save(sensor);

        return measurement;
    }
}


