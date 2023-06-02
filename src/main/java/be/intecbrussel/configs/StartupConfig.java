package be.intecbrussel.configs;

import be.intecbrussel.models.Task;
import be.intecbrussel.repositories.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class StartupConfig implements CommandLineRunner {

    private final TaskRepository taskRepository;

    public StartupConfig(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        List<Task> tasks = new ArrayList<>();

        Task task1 = new Task();
        task1.setTitle("Title task 1");
        task1.setDescription("Description task 1");
        task1.setStartDate(LocalDate.now());
        task1.setEndDate(LocalDate.now());
        task1.setStartTime(LocalTime.of(10, 30));
        task1.setEndTime(LocalTime.of(22, 30));
        tasks.add(task1);

        Task task2 = new Task();
        task2.setTitle("Title task 2");
        task2.setDescription("Description task 2");
        task2.setStartDate(LocalDate.now());
        task2.setEndDate(LocalDate.now().plusDays(1L));
        task2.setStartTime(LocalTime.of(5, 30));
        task2.setEndTime(LocalTime.of(18, 30));
        tasks.add(task2);

        Task task3 = new Task();
        task3.setTitle("Title task 3");
        task3.setDescription("Description task 3");
        task3.setStartDate(LocalDate.now().plusDays(1L));
        task3.setFullDay(true);
        tasks.add(task3);

        Task task4 = new Task();
        task4.setTitle("Title task 4");
        task4.setDescription("Description task 4");
        task4.setStartDate(LocalDate.now().plusMonths(1L));
        task4.setEndDate(LocalDate.now().plusMonths(1L));
        task4.setStartTime(LocalTime.of(5, 30));
        task4.setEndTime(LocalTime.of(18, 30));
        task4.setFullDay(false);
        tasks.add(task4);

        this.taskRepository.saveAll(tasks);
    }
}
