package com.mohsen.todo.controller;

import com.mohsen.todo.exception.GenericError;
import com.mohsen.todo.exception.TaskNotFoundError;
import com.mohsen.todo.exception.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalTime;

@ControllerAdvice
public class TaskControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<GenericError> handleGenericException(Exception exception) {
        GenericError error = new GenericError();
        error.setMessage(exception.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setTimeStamp(LocalTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<TaskNotFoundError> handleSongNotFoundException(TaskNotFoundException exception) {
        TaskNotFoundError error = new TaskNotFoundError();
        error.setMessage(exception.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTimeStamp(LocalTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
