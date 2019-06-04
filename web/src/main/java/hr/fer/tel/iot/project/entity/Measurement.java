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

    private String parameter;
    private Double value;
    private Boolean presence;

//    private Double temperature;
//    private Double brightness;
//    private Boolean presence;



    private Long time;

    @ManyToOne
    @JsonBackReference
    private Sensor sensor;

    public Measurement(){
        this("temperature",10.0,true,1L,new Sensor());
    }
    public Measurement(String parameter, Double value,Boolean presence, Long time, Sensor sensor){
       this.parameter=parameter;
       this.presence=presence;
       this.value=value;
        this.time=time;
        this.sensor=sensor;
    }

    public Long getId() {
        return id;
    }

    public Double getValue() {
        return value;
    }

    public Boolean getPresence() {
        return presence;
    }

    public String getParameter() {
        return parameter;
    }

    public Sensor getSensor() {
        return sensor;
    }


    public Long getTime() {
        return time;
    }

    @Override
    public String toString(){
        return "Measurement{"+
                "id="+ id +
//                ", temperature=" + temperature +
//                ", brightness=" + brightness +
                ", sensorid=" + sensor.getId()+
                "}";
    }
}
