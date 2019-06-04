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

    public static final Long NOW=System.currentTimeMillis()/1000L;

    @Override
    public void run(String... args) throws Exception {

        Sensor sensor=new Sensor("1","Senzor temperature u sobi");
        sensorRepository.save(sensor);

        Measurement measurement=new Measurement("temperature",17.0,true,1559668188L,sensor);
        measurementRepository.save(measurement);

        Sensor sensor1=new Sensor("2","Senzor vlage u sobi");
        sensorRepository.save(sensor1);

        Measurement measurement1=new Measurement("humidity",22.2,null,NOW,sensor1);
        measurementRepository.save(measurement1);

        Sensor sensor2=new Sensor("3","Senzor svjetline u sobi");
        sensorRepository.save(sensor2);
        Measurement measurement2=new Measurement("brightness",34.0,null,NOW, sensor2);
        measurementRepository.save(measurement2);

        Sensor sensor3=new Sensor("4","Vanjski senzor za temperaturu");
        sensorRepository.save(sensor3);

        Measurement measurement3=new Measurement("temperature",25.0,null,NOW,sensor3);
        measurementRepository.save(measurement3);
    }
}
