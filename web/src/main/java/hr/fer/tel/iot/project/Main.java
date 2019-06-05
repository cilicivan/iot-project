package hr.fer.tel.iot.project;

import hr.fer.tel.iot.project.entity.Measurement;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static final String url="http://localhost:8090/measurements";
    private static final String USER_AGENT = "Mozilla/5.0";


    public static void main(String[] args) throws IOException {
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("admin","admin");

        MultiValueMap<String, Object> map= new LinkedMultiValueMap<>();
        map.add("sensorSerial", "1");
        map.add("parameter","humidity");
        map.add("value",4.52);
        map.add("time",14522L);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);

        for(int i=0;i<100;i++){
            restTemplate.exchange(url,HttpMethod.POST,request,Measurement.class);

        }

         //       restTemplate.postForObject( url, request , Measurement.class );



    }
}
