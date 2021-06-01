package cz.fi.muni.pa165.project.controllers;

import cz.fi.muni.pa165.project.exceptions.AirportManagerException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;


/**
 * @author Petr Hendrych
 * @created 27.05.2021
 * @project airport-manager
 **/

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    ResponseEntity<String> handleDataAccessException(DataAccessException ex) {
        return new ResponseEntity<>("Internal error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    ResponseEntity<String> handleAirportManagerException(AirportManagerException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
