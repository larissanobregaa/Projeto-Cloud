package br.edu.ibmec.projeto_cloud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ValidationErrorAdvice {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationMessageErrors validationHandler(MethodArgumentNotValidException e) {
        ValidationMessageErrors response = new ValidationMessageErrors();
        for (FieldError item : e.getFieldErrors()) {
            response.addError(item.getField(), item.getDefaultMessage());
        }
        return response;
    }

    @ExceptionHandler(UsuarioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationMessageErrors validationHandlerUsuario(UsuarioException e ) {
        ValidationMessageErrors response = new ValidationMessageErrors();
        response.addError("exception",e.getMessage());
        return response;
    }
}
