package be.intecbrussel.services;

import be.intecbrussel.dtos.CreateNewTaskRequest;
import be.intecbrussel.dtos.TaskResponse;
import be.intecbrussel.dtos.UpdateTaskRequest;
import be.intecbrussel.exceptions.TaskNotFoundException;
import be.intecbrussel.mappers.TaskMapper;
import be.intecbrussel.models.Task;
import be.intecbrussel.repositories.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

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

    public ResponseEntity<TaskResponse> updateTask(UpdateTaskRequest req) {

        Optional<Task> foundTask = this.taskRepository.findById(req.getId());

        if (!foundTask.isPresent()) {
            throw new TaskNotFoundException("The Task with id : " + req.getId() + " does not exist");
        }

        Task updatedTask = this.taskRepository.save(this.taskMapper.toEntity(req));

        return ResponseEntity.ok(taskMapper.toDto(updatedTask));
    }

    public ResponseEntity<Void> deleteTask(Long id) {
        Optional<Task> foundTask = this.taskRepository.findById(id);

        if (!foundTask.isPresent()) {
            throw new TaskNotFoundException("The Task with id : " + id + " does not exist");
        }

        this.taskRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<TaskResponse> findTask(Long id) {

        Optional<Task> foundTask = this.taskRepository.findById(id);

        if (!foundTask.isPresent()) {
            throw new TaskNotFoundException("The Task with id : " + id + " does not exist");
        }

        return ResponseEntity.ok(this.taskMapper.toDto(foundTask.get()));
    }

    private List<Task> sortTasks(List<Task> tasksToSort, LocalDate date) {
        List<Task> tasks;

        if (!tasksToSort.isEmpty()) {

            List<Task> fullDayTasks = tasksToSort.stream()
                    .filter(Task::isFullDay)
                    .collect(Collectors.toList());

            List<Task> beginDifferentDayTasks = tasksToSort.stream()
                    .filter(task -> task.getStartDate() != date && !task.isFullDay())
                    .collect(Collectors.toList());

            List<Task> beginSameDayTasks = tasksToSort.stream()
                    .filter(task -> task.getStartDate() == date && !task.isFullDay())
                    .sorted(Comparator.comparing(Task::getStartTime))
                    .collect(Collectors.toList());

            tasks = new ArrayList<>(fullDayTasks);
            tasks.addAll(beginDifferentDayTasks);
            tasks.addAll(beginSameDayTasks);
        } else {
            tasks = Collections.emptyList();
        }

        return tasks;
    }

    public ResponseEntity<List<TaskResponse>> findAllTasksFromDay(LocalDate date) {

        List<Task> foundTasks = this.taskRepository.findAllByDate(date);

        List<Task> tasks = this.sortTasks(foundTasks, date);

        return ResponseEntity.ok(this.taskMapper.toDto(tasks));
    }

    public ResponseEntity<List<TaskResponse>> findAllTasksFromMonthOfYear(LocalDate date) {

        int lastDayOfMonth = YearMonth.of(date.getYear(), date.getMonth()).lengthOfMonth();

        LocalDate firstDayOfMonthOfYear = LocalDate.of(date.getYear(), date.getMonth(), 1);
        LocalDate lastDayOfMonthOfYear = LocalDate.of(date.getYear(), date.getMonth(), lastDayOfMonth);

        List<Task> foundTasks = this.taskRepository.findAllByStartDateBetween(firstDayOfMonthOfYear, lastDayOfMonthOfYear);

        List<Task> tasks = foundTasks.stream()
                .sorted(Comparator.comparing(Task::getStartDate))
                .collect(Collectors.toList());

        return ResponseEntity.ok(this.taskMapper.toDto(tasks));
    }

    public ResponseEntity<List<TaskResponse>> findAllTasks() {

        List<Task> tasks = this.taskRepository.findAll();

        return ResponseEntity.ok(this.taskMapper.toDto(tasks));
    }
}
