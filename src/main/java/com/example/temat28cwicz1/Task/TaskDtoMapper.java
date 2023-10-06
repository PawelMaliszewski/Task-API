package com.example.temat28cwicz1.Task;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TaskDtoMapper {

    public TaskDto convertTaskToTaskDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setDeadLine(task.getDeadLine());
        return taskDto;
    }

    public Task convertToTask(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setCreated(LocalDate.now());
        task.setDeadLine(taskDto.getDeadLine());
        task.setFinished(taskDto.isFinished());
        return task;
    }
}
