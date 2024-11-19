package br.edu.ibmec.projeto_cloud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class ValidationErrorAdvice {

    // Método para capturar exceções do tipo UsuarioException
    @ExceptionHandler(UsuarioException.class)
    @ResponseBody
    public ValidationMessageErrors handleUsuarioException(UsuarioException e) {
        ValidationMessageErrors response = new ValidationMessageErrors();
        response.addError("exception", e.getMessage());
        return response;
    }

    // Método para capturar exceções de validação de argumento
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationMessageErrors validationHandler(MethodArgumentNotValidException e) {
        ValidationMessageErrors response = new ValidationMessageErrors();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            response.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return response;
    }
}

