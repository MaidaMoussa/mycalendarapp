package be.intecbrussel.controllers;

import be.intecbrussel.dtos.CreateNewTaskRequest;
import be.intecbrussel.dtos.TaskResponse;
import be.intecbrussel.dtos.UpdateTaskRequest;
import be.intecbrussel.services.TaskService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@OpenAPIDefinition(info = @Info(title = "Human cloning API",
        description = "API to create, manipulate and delete tasks",
        version = "1.0",
        contact = @Contact(
                name = "Kadir, Moussa",
                email = "kadir_moussa@gmail.com",
                url = "https://github.com/MaidaMoussa/mycalendarapp"
        ),
        license = @License(
                name = "MIT Licence",
                url = "https://opensource.org/licenses/mit-license.php"
        )
))
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Creates a new task ")
    @PostMapping("/create")
    public ResponseEntity<TaskResponse> createTask(@RequestBody CreateNewTaskRequest req) {
        return this.taskService.createTask(req);
    }

    @Operation(summary = "Updates a task ")
    @PutMapping("/update")
    public ResponseEntity<TaskResponse> updateTask(@RequestBody UpdateTaskRequest req) {
        return this.taskService.updateTask(req);
    }

    @Operation(summary = "Deletes a  task ")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        return this.taskService.deleteTask(id);
    }

    @Operation(summary = "Finds a task using its id")
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> findTask(@PathVariable Long id) {
        return this.taskService.findTask(id);
    }

    @Operation(summary = "Finds the list of all tasks of a given day ")
    @GetMapping("/{date}")
    public ResponseEntity<List<TaskResponse>> findAllTasks(@PathVariable LocalDate date) {
        return this.taskService.findAllTasks(date);
    }

    //@Operation(summary = "Finds the list of all tasks of a given day ")
    @GetMapping("/")
    public ResponseEntity<List<TaskResponse>> findAllTasksTest() {
        return this.taskService.findAllTasksTest();
    }

}
