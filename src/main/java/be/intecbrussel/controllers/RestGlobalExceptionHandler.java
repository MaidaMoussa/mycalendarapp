package be.intecbrussel.controllers;

import be.intecbrussel.Exceptions.TaskAlreadyExistsException;
import be.intecbrussel.Exceptions.TaskNotFoundException;
import be.intecbrussel.dtos.ErrorResponse;
import be.intecbrussel.exceptions.TaskContentValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = TaskAlreadyExistsException.class)
    protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {

        String type = "https://localhost/errors/insert-conflict";
        String title = "The resource already exists";
        String status = "409";
        String detail = ex.getMessage();
        String instance = request.getDescription(false).substring(4);

        ErrorResponse errorResponse = new ErrorResponse(type, title, status, detail, instance);

        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = TaskNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        String type = "https://localhost/errors/not-found";
        String title = "The resource cannot be found ";
        String status = "404";
        String detail = ex.getMessage();
        String instance = request.getDescription(false).substring(4);

        ErrorResponse errorResponse = new ErrorResponse(type, title, status, detail, instance);

        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {NumberFormatException.class, MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<Object> handleNumericFormatError(Exception ex, WebRequest request) {
        String type = "https://localhost/errors/failed-validation";
        String title = "The resource failed validation";
        String status = "400";
        String detail = ex.getMessage();
        String instance = request.getDescription(false).substring(4);

        ErrorResponse errorResponse = new ErrorResponse(type, title, status, detail, instance);

        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String type = "https://localhost/errors/failed-validation";
        String title = "The resource failed validation";
        String statusCode = "400";
        String detail = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
        String instance = request.getDescription(false).substring(4);

        ErrorResponse errorResponse = new ErrorResponse(type, title, statusCode, detail, instance);

        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String type = "https://localhost/errors/failed-validation";
        String title = "At least one of the given values failed validation";
        String statusCode = "422";
        //String detail ="";
        String instance = request.getDescription(false).substring(4);
        List<String> errors = new ArrayList<>();

        ex.getBindingResult().getAllErrors()
                .stream()
                .filter(FieldError.class::isInstance)
                .map(FieldError.class::cast)
                .forEach(fieldError -> errors.add(fieldError.getField() + " " + fieldError.getDefaultMessage()));

        ErrorResponse errorResponse = new ErrorResponse(type, title, statusCode, errors.toString(), instance);

        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    protected ResponseEntity<Object> handleURLPathErrors(ConstraintViolationException ex, WebRequest request) {
        String type = "https://localhost/errors/failed-validation";
        String title = "The resource URL failed validation";
        String status = "400";
        String detail = ex.getMessage();
        String instance = request.getDescription(false).substring(4);

        ErrorResponse errorResponse = new ErrorResponse(type, title, status, detail, instance);

        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = TaskContentValidationException.class)
    protected ResponseEntity<Object> handleTaskContentError(Exception ex, WebRequest request) {
        String type = "https://localhost/errors/failed-validation";
        String title = "The resource failed validation";
        String status = "400";
        String detail = ex.getMessage();
        String instance = request.getDescription(false).substring(4);

        ErrorResponse errorResponse = new ErrorResponse(type, title, status, detail, instance);

        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> defaultErrorHandler(Exception ex, WebRequest request) throws Exception {
        String type = "https://localhost/errors/unknown-error";
        String title = "An unknown error has occurred";
        String status = "500";
        String detail = ex.getMessage();
        String instance = request.getDescription(false).substring(4);

        ErrorResponse errorResponse = new ErrorResponse(type, title, status, detail, instance);

        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
