package be.intecbrussel.controllers;

import be.intecbrussel.dtos.CreateNewTaskRequest;
import be.intecbrussel.dtos.TaskResponse;
import be.intecbrussel.dtos.UpdateTaskRequest;
import be.intecbrussel.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping(path = "/tasks")
@Validated
@Tag(name = "Task", description = "Manage tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Creates a new task ")
    @PostMapping(path = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponse> createTask(
            @RequestBody
            @NotNull
            @Parameter(required = true, description = "new task info") CreateNewTaskRequest req) {

        return this.taskService.createTask(req);
    }

    @Operation(summary = "Updates a task ")
    @PutMapping(path = "/update",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponse> updateTask(
            @RequestBody
            @NotNull
            @Parameter(required = true, description = "task update info") UpdateTaskRequest req) {

        return this.taskService.updateTask(req);
    }

    @Operation(summary = "Deletes a  task ")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable
            @NotBlank
            @PositiveOrZero
            @Parameter(required = true, description = "task id value positive number", example = "1") Long id) {

        return this.taskService.deleteTask(id);
    }

    @Operation(summary = "Finds a task using its id")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponse> findTask(
            @PathVariable
            @NotBlank
            @PositiveOrZero
            @Parameter(required = true, description = "task id value positive number", example = "1") Long id) {

        return this.taskService.findTask(id);
    }

    @Operation(summary = "Finds the list of all tasks of a given day ")
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskResponse>> findAllTasks(
            @RequestParam(name = "date")
            @NotBlank
            @Parameter(required = true, description = "day when task is active", example = "22/07/2022")
            @DateTimeFormat(pattern = "dd/MM/yyyy")
            LocalDate date) {

        return this.taskService.findAllTasks(date);
    }

    @Operation(summary = "Finds the list of all tasks")
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskResponse>> findAllTasksTest() {

        return this.taskService.findAllTasksTest();
    }

}
