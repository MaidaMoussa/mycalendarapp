package be.intecbrussel.services;

import be.intecbrussel.Exceptions.TaskNotFoundException;
import be.intecbrussel.dtos.CreateNewTaskRequest;
import be.intecbrussel.dtos.TaskResponse;
import be.intecbrussel.dtos.UpdateTaskRequest;
import be.intecbrussel.mappers.TaskMapper;
import be.intecbrussel.models.Task;
import be.intecbrussel.repositories.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;


    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public ResponseEntity<TaskResponse> createTask(CreateNewTaskRequest req) {

        Task newTask = taskMapper.toEntity(req);

        newTask = taskRepository.save(newTask);

        return ResponseEntity.ok(taskMapper.toDto(newTask));
    }

    public ResponseEntity<Void> deleteTask(Long id) {
        Optional<Task> foundTask = this.taskRepository.findById(id);

        if (!foundTask.isPresent()) {
            throw new TaskNotFoundException("The Task with id : " + id + " does not exist");
        }

        this.taskRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<TaskResponse> updateTask(UpdateTaskRequest req) {

        Optional<Task> foundTask = this.taskRepository.findById(req.getId());

        if (!foundTask.isPresent()) {
            throw new TaskNotFoundException("The Task with id : " + req.getId() + " does not exist");
        }

        Task updatedTask = this.taskRepository.save(this.taskMapper.toEntity(req));

        return ResponseEntity.ok(taskMapper.toDto(updatedTask));
    }


    public ResponseEntity<TaskResponse> findTask(Long id) {

        Optional<Task> foundTask = this.taskRepository.findById(id);

        if (!foundTask.isPresent()) {
            throw new TaskNotFoundException("The Task with id : " + id + " does not exist");
        }

        return ResponseEntity.ok(this.taskMapper.toDto(foundTask.get()));
    }

    public ResponseEntity<List<TaskResponse>> findAllTasks(LocalDate date) {

        List<Task> tasks = this.taskRepository.findAllByDate(date);

        return ResponseEntity.ok(this.taskMapper.toDto(tasks));
    }

    public ResponseEntity<List<TaskResponse>> findAllTasksTest() {

        List<Task> tasks = this.taskRepository.findAll();

        return ResponseEntity.ok(this.taskMapper.toDto(tasks));
    }


}
