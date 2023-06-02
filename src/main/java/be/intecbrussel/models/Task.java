package be.intecbrussel.models;

import be.intecbrussel.validators.ValidateTaskDates;
import be.intecbrussel.validators.ValidateTaskHours;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@ValidateTaskDates
@ValidateTaskHours
@Getter
@Setter
@NoArgsConstructor
@Table(name = "task")
@Entity
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "The start date is mandatory")
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "start_time")
    private LocalTime startTime;
    @Column(name = "end_time")
    private LocalTime endTime;

    @NotBlank(message = "The title must not be empty")
    private String title;
    private String description;

    private boolean fullDay;
}
