package hr.fer.tel.iot.project;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class Main {
    public static final String url="http://localhost:8090/measurements";
    public static void main(String[] args){
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("admin","admin");
        JSONObject jsonObject=new JSONObject();
        jsonObject.accumulate("sensorSerial","1");
        jsonObject.accumulate("parameter","temperature");
        jsonObject.accumulate("value",4.5);
        jsonObject.accumulate("time",2342L);

        System.out.println(jsonObject);

        HttpEntity<String> request=new HttpEntity<>(jsonObject.toString(), headers);
        restTemplate.exchange(url, HttpMethod.POST,request,String.class);


    }
}
