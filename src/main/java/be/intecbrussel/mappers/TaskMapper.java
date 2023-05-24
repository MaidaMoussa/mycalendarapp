package be.intecbrussel.mappers;

import be.intecbrussel.dtos.CreateNewTaskRequest;
import be.intecbrussel.dtos.TaskResponse;
import be.intecbrussel.dtos.UpdateTaskRequest;
import be.intecbrussel.models.Task;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toEntity(CreateNewTaskRequest req);

    Task toEntity(UpdateTaskRequest req);

    TaskResponse toDto(Task task);

    List<TaskResponse> toDto(List<Task> tasks);

}
