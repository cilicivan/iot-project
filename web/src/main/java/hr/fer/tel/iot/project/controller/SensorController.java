package hr.fer.tel.iot.project.controller;

import hr.fer.tel.iot.project.entity.Sensor;
import hr.fer.tel.iot.project.repository.SensorRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/sensors")
public class SensorController {
    public static Double temperature=20.0;
    public static Double brightness=25.6;
    public static String state="good";

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

    @GetMapping(value = "params")
    public String getParams(){
        System.out.println("Dobio get, temp"+ temperature + ", bri" + brightness);
        JSONObject jsonObject=new JSONObject();
        jsonObject.accumulate("temperature",temperature);
        jsonObject.accumulate("brightness",brightness);
        return jsonObject.toString();
    }
    @PostMapping(value = "/params")
    public String setParams(@RequestParam(value = "temperature") String setTemperature,
                                @RequestParam(value = "brightness") String setBrightness){

        System.out.println("dobio post");
        Double temperatureValue=Double.parseDouble(setTemperature);
        Double brightnessValue=Double.parseDouble(setBrightness);
        temperature=temperatureValue;
        brightness=brightnessValue;
        JSONObject jsonObject=new JSONObject();
        jsonObject.accumulate("temperature",temperature);
        jsonObject.accumulate("brightness",brightness);
        return jsonObject.toString();
    }

}
