package hr.fer.tel.iot.project.handler;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(RuntimeException.class)
    public String handle() {
        return "/404";
    }
}
