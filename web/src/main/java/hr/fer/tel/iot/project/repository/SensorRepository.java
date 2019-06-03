package hr.fer.tel.iot.project.repository;

import hr.fer.tel.iot.project.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor,Long> {
    public Sensor findBySerial(String serial);
}
