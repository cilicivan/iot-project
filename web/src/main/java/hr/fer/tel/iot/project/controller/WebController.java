package hr.fer.tel.iot.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class WebController {

    @GetMapping(value = "/")
    public String index(){
        return "index.html";
    }

    @GetMapping(value = "/error")
    public String error(){
        return "404.html";
    }

}
