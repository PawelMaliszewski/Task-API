package com.example.temat28cwicz1.Task;

import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskDtoMapper taskDtoMapper;

    public TaskService(TaskRepository taskRepository, TaskDtoMapper taskDtoMapper) {
        this.taskRepository = taskRepository;
        this.taskDtoMapper = taskDtoMapper;
    }

    public List<TaskDto> getListOfTaskDyo() {
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskRepository.findAll().forEach(task -> taskDtoList.add(taskDtoMapper.convertTaskToTaskDto(task)));
        return taskDtoList;
    }

    public Optional<TaskDto> getTaskDtoById(Long id) {
        return taskRepository.findById(id).map(taskDtoMapper::convertTaskToTaskDto);
    }


    public TaskDto save(TaskDto taskDto) {
        Task task = taskRepository.save(taskDtoMapper.convertToTask(taskDto));
        taskDto.setId(task.getId());
        return taskDto;
    }

    Optional<TaskDto> replaceTask(Long id, TaskDto taskDto) {
        if (taskRepository.existsById(id)) {
            taskDto.setId(id);
            taskRepository.save(taskDtoMapper.convertToTask(taskDto));
            return Optional.of(taskDto);
        }
        return Optional.empty();
    }
}
