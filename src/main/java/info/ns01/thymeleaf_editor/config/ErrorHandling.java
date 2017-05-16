package info.ns01.thymeleaf_editor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ControllerAdvice
@EnableWebMvc
public class ErrorHandling {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @Order(Ordered.LOWEST_PRECEDENCE)
    public String returnGeneralError(Exception err) {
        logger.error("Exception occurred: ", err);
        return "error_pages/500";
    }
    
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Order(Ordered.HIGHEST_PRECEDENCE+100)
    public String handleNoHandler(NoHandlerFoundException err){
        return "error_pages/404";
    }
    
}
