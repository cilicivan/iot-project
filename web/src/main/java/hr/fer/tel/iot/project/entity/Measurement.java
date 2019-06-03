package hr.fer.tel.iot.project.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Measurement {

    @Id @GeneratedValue
    private Long id;

    private double temperature;
    private double brightness;

    @ManyToOne
    @JsonBackReference
    private Sensor sensor;

    public Measurement(){
        this(10.0,10.0,new Sensor());
    }
    public Measurement(double temperature, double brightness, Sensor sensor){
        this.temperature=temperature;
        this.brightness=brightness;
        this.sensor=sensor;
    }
}
