package hr.fer.tel.iot.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping(value = "/")
    public String index(){
        return "index.html";
    }

    @GetMapping(value = "/form")
    public String form(){
        return "form.html";
    }

    @GetMapping(value = "/404")
    public String error(){
        return "error.html";
    }

}
