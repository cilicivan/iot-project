package hr.fer.tel.iot.project.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity

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

    public Long getId() {
        return id;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getBrightness() {
        return brightness;
    }

    public Sensor getSensor() {
        return sensor;
    }

    @Override
    public String toString(){
        return "Measurement{"+
                "id="+ id +
                ", temperature=" + temperature +
                ", brightness=" + brightness +
                ", sensorid=" + sensor.getId()+
                "}";
    }
}
