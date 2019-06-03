package hr.fer.tel.iot.project;

import hr.fer.tel.iot.project.entity.Measurement;
import hr.fer.tel.iot.project.entity.Sensor;
import hr.fer.tel.iot.project.repository.MeasurementRepository;
import hr.fer.tel.iot.project.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbInit implements CommandLineRunner {
    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private MeasurementRepository measurementRepository;

    @Override
    public void run(String... args) throws Exception {

        Sensor sensor=new Sensor("123-sdfb-234-ASA");
        sensorRepository.save(sensor);

        Measurement measurement=new Measurement(24.5,8.3,sensor);
        measurementRepository.save(measurement);
    }
}
