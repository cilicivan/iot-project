//package hr.fer.tel.iot.project.handler;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//@ControllerAdvice
//public class ExceptionHandler {
//
//    private static Logger log = LoggerFactory.getLogger(ExceptionHandler.class);
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler
//    public void notFoundHandler() {
//        log.debug("Item not found. HTTP 500 returned.");
//    }
//
//}
