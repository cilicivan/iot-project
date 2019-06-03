package hr.fer.tel.iot.project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Sensor {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String serial;

    @OneToMany(mappedBy = "sensor")
    @JsonManagedReference
    private List<Measurement> measurements;

    public Sensor(){
        this("default-serial-number");
    }

    public Sensor(String serial){
        this.serial=serial;
        this.measurements=new ArrayList<>();
    }
}
