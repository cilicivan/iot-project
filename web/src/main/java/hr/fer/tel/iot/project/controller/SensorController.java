package hr.fer.tel.iot.project.controller;

import hr.fer.tel.iot.project.entity.Sensor;
import hr.fer.tel.iot.project.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Sensor getSensor(@PathVariable Long id){
        return sensorRepository.findById(id).orElseThrow(() -> new RuntimeException("Sensor does not exit"));
    }

    @PostMapping
    public Sensor addSensor(@RequestParam(value = "serial") String serial,
                            @RequestParam(value="name") String name){
        Sensor sensor=new Sensor(serial,name);
        return sensorRepository.save(sensor);

    }

}
