package be.intecbrussel.services;

import be.intecbrussel.Exceptions.TaskNotFoundException;
import be.intecbrussel.dtos.CreateNewTaskRequest;
import be.intecbrussel.dtos.TaskResponse;
import be.intecbrussel.dtos.UpdateTaskRequest;
import be.intecbrussel.exceptions.TaskContentValidationException;
import be.intecbrussel.mappers.TaskMapper;
import be.intecbrussel.models.Task;
import be.intecbrussel.repositories.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    private void validateTask(CreateNewTaskRequest req) {

        if (req.getStartDate() == null) {

            throw new TaskContentValidationException("A task must always have a start date ");
        } else if (req.getEndDate() != null) {

            if (req.getStartDate().isAfter(req.getEndDate())) {

                throw new TaskContentValidationException("A task start date must be before its end date " +
                        "given : start '" + req.getStartDate() + "' end '" + req.getEndDate() + "'");
            } else if (req.getStartDate().isEqual(req.getEndDate())) {

                if (!req.isFullDay()) {

                    if (req.getStartTime() == null || req.getEndTime() == null) {
                        throw new TaskContentValidationException("A normal task on same day must have " +
                                "a start time and an end time given : start '" + req.getStartTime()
                                + "' end '" + req.getEndTime() + "'");

                    } else if (!req.getEndTime().isAfter(req.getStartTime())) {
                        throw new TaskContentValidationException("For same day task " +
                                "start time must be before end time given : start '" + req.getStartTime()
                                + " end '" + req.getEndTime() + "'");
                    }
                }
            } else {
                if (req.getStartTime() == null || req.getEndTime() == null) {
                    throw new TaskContentValidationException("A normal task must have " +
                            "a start time and an end time given : start '" + req.getStartTime()
                            + "' end '" + req.getEndTime() + "'");
                }
            }

            if (req.isFullDay() && (req.getStartTime() != null || req.getEndTime() != null)) {
                throw new TaskContentValidationException("A full day task cannot have a star time or an end time ");
            }

            if (req.getTitle() == null || req.getTitle().trim().isEmpty()) {
                throw new TaskContentValidationException("A task title cannot be empty ");
            }
        }
    }

    public ResponseEntity<TaskResponse> createTask(CreateNewTaskRequest req) {

        validateTask(req);

        Task newTask = taskMapper.toEntity(req);

        newTask = taskRepository.save(newTask);

        return ResponseEntity.ok(taskMapper.toDto(newTask));
    }


    private void validateTask(UpdateTaskRequest req) {

        if (req.getId() < 0L) {
            throw new TaskContentValidationException("A task id must be a positive integer or zero " +
                    "given : id " + req.getId());
        }

        if (req.getStartDate() == null) {

            throw new TaskContentValidationException("A task must always have a start date ");
        } else if (req.getEndDate() != null) {

            if (req.getStartDate().isAfter(req.getEndDate())) {

                throw new TaskContentValidationException("A task start date must be before its end date " +
                        "given : start '" + req.getStartDate() + "' end '" + req.getEndDate() + "'");
            } else if (req.getStartDate().isEqual(req.getEndDate())) {

                if (!req.isFullDay()) {

                    if (req.getStartTime() == null || req.getEndTime() == null) {
                        throw new TaskContentValidationException("A normal task on same day must have " +
                                "a start time and an end time given : start '" + req.getStartTime()
                                + "' end '" + req.getEndTime() + "'");

                    } else if (!req.getEndTime().isAfter(req.getStartTime())) {
                        throw new TaskContentValidationException("For same day task " +
                                "start time must be before end time given : start '" + req.getStartTime()
                                + " end '" + req.getEndTime() + "'");
                    }
                }
            } else {
                if (req.getStartTime() == null || req.getEndTime() == null) {
                    throw new TaskContentValidationException("A normal task must have " +
                            "a start time and an end time given : start '" + req.getStartTime()
                            + "' end '" + req.getEndTime() + "'");
                }
            }

            if (req.isFullDay() && (req.getStartTime() != null || req.getEndTime() != null)) {
                throw new TaskContentValidationException("A full day task cannot have a star time or an end time ");
            }

            if (req.getTitle() == null || req.getTitle().trim().isEmpty()) {
                throw new TaskContentValidationException("A task title cannot be empty ");
            }
        }
    }


    public ResponseEntity<TaskResponse> updateTask(UpdateTaskRequest req) {

        validateTask(req);

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

    public ResponseEntity<List<TaskResponse>> findAllTasks(LocalDate date) {

        List<Task> foundTasks = this.taskRepository.findAllByDate(date);

        List<Task> tasks = this.sortTasks(foundTasks, date);

        return ResponseEntity.ok(this.taskMapper.toDto(tasks));
    }

    public ResponseEntity<List<TaskResponse>> findAllTasks() {

        List<Task> tasks = this.taskRepository.findAll();

        return ResponseEntity.ok(this.taskMapper.toDto(tasks));
    }

}
