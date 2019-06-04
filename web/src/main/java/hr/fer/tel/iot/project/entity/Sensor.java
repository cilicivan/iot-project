package hr.fer.tel.iot.project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Sensor {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String serial;

    private String name;

    @OneToMany(mappedBy = "sensor")
    @JsonManagedReference
    private List<Measurement> measurements;

    public Sensor(){
        this("default-serial-number","default-name");
    }

    public Sensor(String serial, String name){
        this.serial=serial;
        this.name=name;
        this.measurements=new ArrayList<>();
    }

    public Long getId() {
        return id;
    }


    public String getSerial() {
        return serial;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return "Sensor{" +
                ", id=" + id +
                ", serial=" + serial +
                ", measurements=" +
                "}";

    }

}
