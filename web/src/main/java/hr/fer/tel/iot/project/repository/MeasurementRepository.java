package hr.fer.tel.iot.project.repository;

import hr.fer.tel.iot.project.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement,Long> {
}
