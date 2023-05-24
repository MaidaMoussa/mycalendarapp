package be.intecbrussel.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@Jacksonized
@Builder
public class CreateNewTaskRequest {

    @Schema(type = "string", pattern = "dd/MM/yyyy", example = "24/10/2022")
    private LocalDate startDate;
    @Schema(type = "string", pattern = "dd/MM/yyyy", example = "24/10/2022")
    private LocalDate endDate;

    @Schema(example = "19:30")
    private LocalTime startTime;
    @Schema(example = "19:30")
    private LocalTime endTime;

    private String title;
    private String description;

    private boolean fullDay;

}
