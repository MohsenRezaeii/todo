package com.mohsen.todo.controller;

import com.mohsen.todo.dto.TaskDto;
import com.mohsen.todo.entity.Task;
import com.mohsen.todo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/tasks")
@Tag(name = "ToDo controller")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Create a new task", description = "Creates a new task as per the request body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad request - The task is not valid"),
            @ApiResponse(responseCode = "500", description = "Internal server error - Something went wrong")
    })
    @PostMapping
    public ResponseEntity<Mono<Task>> createNewTask(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Task details", required = true,
                    content = @Content(schema = @Schema(implementation = Task.class)))
            @RequestBody TaskDto taskDto) {
        Mono<Task> createdTask = taskService.createTask(taskDto);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a task by id", description = "Returns a task as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The task was not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Mono<Task>> getTaskById(@PathVariable("id")
                                                  @Parameter(name = "id", description = "Task id", example = "1")
                                                  String id) {
        Mono<Task> foundTask = taskService.getTaskById(id);
        return new ResponseEntity<>(foundTask, HttpStatus.OK);
    }

    @Operation(summary = "Get all tasks", description = "Returns all existing tasks")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Get all tasks")})
    @GetMapping
    public ResponseEntity<Flux<Task>> getAllTasks() {
        Flux<Task> allTasks = taskService.getAllTasks();
        return new ResponseEntity<>(allTasks, HttpStatus.OK);
    }

    @Operation(summary = "Edit a task by id", description = "Finds an existing task by the provided id, and updates it as per the request body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task successfully updated"),
            @ApiResponse(responseCode = "404", description = "Not found - The task was not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Mono<Task>> updateTask(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Task details", required = true,
                    content = @Content(schema = @Schema(implementation = Task.class)))
            @RequestBody TaskDto taskDto,
            @PathVariable("id") @Parameter(name = "id", description = "Task id", example = "1") String id) {
        Mono<Task> updatedTask = taskService.updateTask(taskDto, id);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @Operation(summary = "Delete a task by id", description = "Deletes a task as per the id, then returns it in the response")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The task was not found")
    })
    @DeleteMapping("/{id}")
    public Mono<Task> deleteTask(@PathVariable("id") @Parameter(name = "id", description = "Task id", example = "1")  String id) {
        return taskService.deleteTask(id);
    }

}
